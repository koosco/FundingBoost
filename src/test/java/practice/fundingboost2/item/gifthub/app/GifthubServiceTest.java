package practice.fundingboost2.item.gifthub.app;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.item.gifthub.repo.entity.Gifthub;
import practice.fundingboost2.item.gifthub.repo.entity.GifthubId;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.member.repo.entity.Member;

@Transactional
@SpringBootTest
class GifthubServiceTest {

    @Autowired
    GifthubService gifthubService;

    @PersistenceContext
    EntityManager em;

    Item item;

    Member member;

    @BeforeEach
    void init() {
        item = new Item("item", 1000, "url", "brand", "category", 0, 0);
        em.persist(item);
        em.flush();

        member = new Member("member", "name", "imageUrl", "phone");
        em.persist(member);
        em.flush();
    }

    @Test
    void givenNoGifthub_whenCreateGifthub_thenQuantityMustBeOne() {
        // given
        // when
        CommonSuccessDto dto = gifthubService.addToCart(member.getId(), item.getId());
        Gifthub findGifthub = em.createQuery(
                "select g from Gifthub g where g.id.memberId=:memberId and g.id.itemId=:itemId", Gifthub.class)
            .setParameter("memberId", member.getId())
            .setParameter("itemId", item.getId())
            .getSingleResult();

        // then
        assertTrue(dto.isSuccess());
        assertThat(findGifthub.getQuantity()).isEqualTo(1);
        assertThat(findGifthub.getId().getMemberId()).isEqualTo(member.getId());
        assertThat(findGifthub.getId().getItemId()).isEqualTo(item.getId());
    }

    @Test
    void givenOneGifthub_whenCreateGifthub_thenQuantityMustBeTwo() {
        // given
        Item item2 = new Item("item2", 1000, "url", "brand", "category", 0, 0);
        em.persist(item2);
        em.flush();
        gifthubService.addToCart(member.getId(), item2.getId());

        // when
        CommonSuccessDto dto = gifthubService.addToCart(member.getId(), item2.getId());
        Gifthub findGifthub = em.createQuery(
                "select g from Gifthub g where g.id.memberId=:memberId and g.id.itemId=:itemId", Gifthub.class)
            .setParameter("memberId", member.getId())
            .setParameter("itemId", item2.getId())
            .getSingleResult();

        // then
        assertTrue(dto.isSuccess());
        assertThat(findGifthub.getQuantity()).isEqualTo(2);
        assertThat(findGifthub.getId().getMemberId()).isEqualTo(member.getId());
        assertThat(findGifthub.getId().getItemId()).isEqualTo(item2.getId());
    }

    @Test
    void givenMyGifthub_whenOtherCreateGifthub_thenQuantityMustBeOne() {
        // given
        Member other = new Member("member2", "name", "imageUrl", "phone");
        em.persist(other);
        em.flush();

        gifthubService.addToCart(member.getId(), item.getId());

        // when
        CommonSuccessDto dto = gifthubService.addToCart(other.getId(), item.getId());
        Gifthub findGifthub = em.createQuery(
                "select g from Gifthub g where g.id.memberId=:memberId and g.id.itemId=:itemId", Gifthub.class)
            .setParameter("memberId", other.getId())
            .setParameter("itemId", item.getId())
            .getSingleResult();

        // then
        assertTrue(dto.isSuccess());
        assertThat(findGifthub.getQuantity()).isEqualTo(1);
    }

    @Test
    void GivenItem_whenCreateWithNotExistsItem_thenThrowException() {
        // given
        Long notExistsItemId = 100000000000000L;
        // when
        // then
        assertThrows(CommonException.class, () -> gifthubService.addToCart(member.getId(), notExistsItemId));
    }

    @Test
    void givenGifthub_whenDeleteGifthub_thenMustSuccess() {
        // given
        gifthubService.addToCart(member.getId(), item.getId());
        // when
        gifthubService.deleteGifthub(member.getId(), item.getId());
        // then
        assertThrows(NoResultException.class, () -> em.createQuery("select g from Gifthub g where g.id=:id", Gifthub.class)
            .setParameter("id", new GifthubId(member.getId(), item.getId()))
            .getSingleResult());
    }

    @Test
    void givenNotExistsItem_whenDeleteGifthub_thenThrowException() {
        // given
        Long notExistsItemId = 100000000000000L;

        // when
        // then
        assertThrows(CommonException.class, () -> gifthubService.deleteGifthub(member.getId(), notExistsItemId));
    }
}