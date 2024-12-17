package practice.fundingboost2.item;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.item.item.app.ItemService;
import practice.fundingboost2.item.item.repo.BookmarkRepository;
import practice.fundingboost2.item.item.repo.entity.Bookmark;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.member.repo.entity.Member;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    BookmarkRepository bookmarkRepository;

    @PersistenceContext
    EntityManager em;

    Member member;

    Item item;

    @BeforeEach
    void init() {
        member = new Member("email", "name", "imageUrl", "phone");
        em.persist(member);

        item = new Item("name", 1000, "imageUrl", "brand", "category", 0, 0);
        em.persist(item);

        em.flush();
    }

    @Test
    void givenNotExistsBookmark_whenMark_thenCreateNewBookmark() {
        // given
        Long memberId = member.getId();
        Long itemId = item.getId();
        Bookmark bookmark = new Bookmark(memberId, itemId);

        // when
        CommonSuccessDto dto = itemService.likeItem(memberId, itemId);

        // then
        assertThat(item.getLikeCount()).isEqualTo(1);
        assertTrue(bookmarkRepository.existsById(bookmark.getId()));
    }

    @Test
    void givenExistsBookmark_whenUnMark_thenDeleteBookmark() {
        // given
        Long memberId = member.getId();
        Long itemId = item.getId();
        Bookmark bookmark = new Bookmark(memberId, itemId);
        itemService.likeItem(memberId, itemId);

        // when
        itemService.likeItem(memberId, itemId);

        // then
        assertThat(item.getLikeCount()).isEqualTo(0);
        assertFalse(bookmarkRepository.existsById(bookmark.getId()));
    }
}