package practice.fundingboost2.item.item.repo.jpa;


import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import practice.fundingboost2.item.item.repo.entity.Bookmark;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.repo.entity.Option;
import practice.fundingboost2.item.item.ui.dto.GetItemDetailResponseDto;
import practice.fundingboost2.item.item.ui.dto.GetItemListResponseDto;
import practice.fundingboost2.item.item.ui.dto.GetItemResponseDto;
import practice.fundingboost2.member.repo.entity.Member;

@Transactional
@SpringBootTest
class ItemQueryRepositoryTest {

    @Autowired
    ItemQueryRepository itemQueryRepository;

    @PersistenceContext
    EntityManager em;

    Member member;
    List<Item> items = new ArrayList<>();
    List<Bookmark> bookmarks = new ArrayList<>();

    final int ITEM_SIZE = 12;

    @BeforeEach
    void init() {
        member = new Member("member1", "password", "nickname", "email");
        em.persist(member);

        for (int i = 1; i <= ITEM_SIZE; i++) {
            Item item = new Item(
                "item" + i,
                i * 1000,
                "https://koosco.tistory.com",
                "brand" + i,
                "category" + i
            );

            initCount(item, i);

            em.persist(item);
            items.add(item);

            Option option = new Option(item, "option" + i, i * 10);
            em.persist(option);
        }

        for (Item item : items) {
            Bookmark bookmark = new Bookmark(member.getId(), item.getId());
            em.persist(bookmark);
            bookmarks.add(bookmark);
        }

        em.flush();
    }

    private void initCount(Item item, int i) {
        try {
            Field fundingCountField = Item.class.getDeclaredField("fundingCount");
            Field likeCountField = Item.class.getDeclaredField("likeCount");
            Field reviewCountField = Item.class.getDeclaredField("reviewCount");

            fundingCountField.setAccessible(true);
            likeCountField.setAccessible(true);
            reviewCountField.setAccessible(true);

            fundingCountField.set(item, i);
            likeCountField.set(item, ITEM_SIZE - i);
            reviewCountField.set(item, i * 2);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to set count fields", e);
        }
    }

    @Test
    void givenItems_whenOrderIsFunding_thenReturnItemsOrderByFundingCount() {
        // given
        PageRequest pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("funding")));

        // when
        GetItemListResponseDto dto = itemQueryRepository.getItems(null, pageable);

        // then
        List<GetItemResponseDto> dtos = dto.getItems();
        assertThat(dtos).hasSize(10);
        assertThat(dtos.get(0).getFundingCount()).isGreaterThan(dtos.get(1).getFundingCount());
        assertThat(dtos.get(1).getFundingCount()).isGreaterThan(dtos.get(2).getFundingCount());
    }

    @Test
    void givenItems_whenOrderIsLike_thenReturnItemsOrderByLikeCount() {
        // given
        PageRequest pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("most")));

        // when
        GetItemListResponseDto dto = itemQueryRepository.getItems(null, pageable);

        // then
        List<GetItemResponseDto> dtos = dto.getItems();
        assertThat(dtos).hasSize(10);
        assertThat(dtos.get(0).getLikeCount()).isGreaterThan(dtos.get(1).getLikeCount());
        assertThat(dtos.get(1).getLikeCount()).isGreaterThan(dtos.get(2).getLikeCount());
    }

    @Test
    void givenItems_whenOrderIsReview_thenReturnItemsOrderByReviewCount() {
        // given
        PageRequest pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("review")));

        // when
        GetItemListResponseDto dto = itemQueryRepository.getItems(null, pageable);

        // then
        List<GetItemResponseDto> dtos = dto.getItems();
        assertThat(dtos).hasSize(10);
        assertThat(dtos.get(0).getReviewCount()).isGreaterThan(dtos.get(1).getReviewCount());
        assertThat(dtos.get(1).getReviewCount()).isGreaterThan(dtos.get(2).getReviewCount());
    }

    @Test
    void givenItems_whenOrderIsNull_thenReturnItemsOrderByLikeCount() {
        // given
        PageRequest pageable = PageRequest.of(0, 10);

        // when
        GetItemListResponseDto dto = itemQueryRepository.getItems(null, pageable);

        // then
        List<GetItemResponseDto> dtos = dto.getItems();
        assertThat(dtos).hasSize(10);
        assertThat(dtos.get(0).getLikeCount()).isGreaterThan(dtos.get(1).getLikeCount());
        assertThat(dtos.get(1).getLikeCount()).isGreaterThan(dtos.get(2).getLikeCount());
    }

    @Test
    void givenItems_whenOrderIsInvalid_thenReturnItemsOrderByLikeCount() {
        // given
        PageRequest pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("xxxx")));

        // when
        GetItemListResponseDto dto = itemQueryRepository.getItems(null, pageable);

        // then
        List<GetItemResponseDto> dtos = dto.getItems();
        assertThat(dtos).hasSize(10);
        assertThat(dtos.get(0).getLikeCount()).isGreaterThan(dtos.get(1).getLikeCount());
        assertThat(dtos.get(1).getLikeCount()).isGreaterThan(dtos.get(2).getLikeCount());
    }

    @Test
    void givenItems_whenFindAllItems_thenReturnDtos() {
        // given
        PageRequest pageable = PageRequest.of(0, 10);
        // when
        GetItemListResponseDto dto = itemQueryRepository.getItems(null, pageable);
        // then
        assertThat(dto.getItems()).hasSize(10);
    }

    @Test
    void givenNoItems_whenFindAllItems_thenReturnEmptyItemsDto() {
        // given
        em.createQuery("delete from Option").executeUpdate();
        em.createQuery("delete from Item").executeUpdate();
        em.flush();

        PageRequest pageable = PageRequest.of(1, 10);
        // when
        GetItemListResponseDto dto = itemQueryRepository.getItems(null, pageable);
        // then
        assertThat(dto.getItems()).isEmpty();
    }

    @Test
    void givenItems_whenSearchWithCategory_thenReturnGivenCategoryItems() {
        // given
        PageRequest pageable = PageRequest.of(0, 10);
        // when
        GetItemListResponseDto dto = itemQueryRepository.getItems("category1", pageable);
        // then
        assertThat(dto.getItems()).hasSize(1);
        assertThat(dto.getItems().getFirst().getCategory()).isEqualTo("category1");
    }

    @Test
    void givenItems_whenMemberIdAndPageRequest_thenReturn10Dtos() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        GetItemListResponseDto dto = itemQueryRepository.getLikedItems(member.getId(), pageRequest);

        // then
        assertThat(dto.getItems()).hasSize(10);
    }

    @Test
    void givenItems_whenMemberIdNotFound_thenReturnEmptyDtos() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 10);
        // when
        GetItemListResponseDto dto = itemQueryRepository.getLikedItems(2L, pageRequest);

        // then
        assertThat(dto.getItems()).isEmpty();
    }

    @Test
    void givenItems_whenPageIsOne_thenReturnSecondPageItemsDto() {
        // given
        PageRequest pageRequest = PageRequest.of(1, 10);
        // when
        GetItemListResponseDto dto = itemQueryRepository.getLikedItems(member.getId(), pageRequest);
        // then
        assertThat(dto.getItems()).hasSize(2);
    }

    @Test
    void givenItems_whenPageSizeIsOne_thenReturnOneDtoItem() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 1);
        // when
        GetItemListResponseDto dto = itemQueryRepository.getLikedItems(member.getId(), pageRequest);
        // then
        assertThat(dto.getItems()).hasSize(1);
    }

    @Test
    void givenCreateItem_whenMemberLike_thenReturnDtoWithLike() {
        // given
        Item item = items.getFirst();

        // when
        GetItemDetailResponseDto dto = itemQueryRepository.getItemInfo(member.getId(), item.getId());

        // then
        assertThat(dto.isLiked()).isTrue();
    }

    @Test
    void givenCreateItem_whenMemberDoesNotLike_thenReturnDtoWithOutLike() {
        // given
        Item newItem = new Item("item", 1000, "https://koosco.tistory.com", "brand", "category");
        em.persist(newItem);
        em.flush();

        // when
        GetItemDetailResponseDto dto = itemQueryRepository.getItemInfo(member.getId(), newItem.getId());

        // then
        assertThat(dto.isLiked()).isFalse();
    }

    @Test
    void givenCreateItem_whenMemberIdIsNull_thenReturnDtoWithOutLike() {
        // given
        Item item = items.getFirst();

        // when
        GetItemDetailResponseDto dto = itemQueryRepository.getItemInfo(null, item.getId());

        // then
        assertThat(dto.isLiked()).isFalse();
    }
}