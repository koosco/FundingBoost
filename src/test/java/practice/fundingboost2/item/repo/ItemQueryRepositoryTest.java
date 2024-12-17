package practice.fundingboost2.item.repo;


import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.transaction.annotation.Transactional;
import practice.fundingboost2.item.item.ui.dto.GetItemListResponseDto;
import practice.fundingboost2.item.item.repo.jpa.ItemQueryRepository;
import practice.fundingboost2.item.item.repo.entity.Bookmark;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.repo.entity.Option;
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
    @Autowired
    private PageableHandlerMethodArgumentResolver pageableResolver;

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
                "category" + i,
                (i % 3) * 5,  // popularity
                (i % 3) * 10  // stock count
            );
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

    @Test
    void givenItems_whenFindAllItems_thenReturnDtos() {
        // given
        PageRequest pageable = PageRequest.of(0, 10);
        // when
        GetItemListResponseDto dto = itemQueryRepository.getItems(pageable);
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
        GetItemListResponseDto dto = itemQueryRepository.getItems(pageable);
        // then
        assertThat(dto.getItems()).isEmpty();
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
}