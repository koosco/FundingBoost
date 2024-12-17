package practice.fundingboost2.item.gifthub.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
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
import practice.fundingboost2.item.item.repo.entity.Option;
import practice.fundingboost2.member.repo.entity.Member;

@Transactional
@SpringBootTest
class GifthubServiceCreateTest {

    @Autowired
    GifthubService gifthubService;

    @PersistenceContext
    EntityManager em;

    Item item;

    Member member;

    Option option;

    @BeforeEach
    void init() {
        item = new Item("item", 1000, "url", "brand", "category", 0, 0);
        em.persist(item);
        em.flush();

        List<String> optionNames = List.of("option1", "option2", "option3");
        int optionPrice = 100;
        for (String optionName : optionNames) {
            Option option = new Option(item, optionName, optionPrice);
            em.persist(option);
        }
        option = item.getOptions().getFirst();

        member = new Member("member", "name", "imageUrl", "phone");
        em.persist(member);
        em.flush();
    }

    @Test
    void givenNoGifthub_whenCreateGifthub_thenQuantityMustBeOne() {
        // given
        // when
        CommonSuccessDto dto = gifthubService.addToCart(member.getId(), item.getId(), option.getId());
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
        gifthubService.addToCart(member.getId(), item.getId(), option.getId());

        // when
        CommonSuccessDto dto = gifthubService.addToCart(member.getId(), item.getId(), option.getId());
        Gifthub findGifthub = em.createQuery(
                "select g from Gifthub g where g.id=:id", Gifthub.class)
            .setParameter("id", new GifthubId(member.getId(), item.getId(), option.getId()))
            .getSingleResult();

        // then
        assertTrue(dto.isSuccess());
        assertThat(findGifthub.getQuantity()).isEqualTo(2);
        assertThat(findGifthub.getId().getMemberId()).isEqualTo(member.getId());
        assertThat(findGifthub.getId().getItemId()).isEqualTo(item.getId());
    }

    @Test
    void givenMyGifthub_whenOtherCreateGifthub_thenQuantityMustBeOne() {
        // given
        Member other = new Member("member2", "name", "imageUrl", "phone");
        em.persist(other);
        em.flush();

        gifthubService.addToCart(member.getId(), item.getId(), option.getId());

        // when
        CommonSuccessDto dto = gifthubService.addToCart(other.getId(), item.getId(), option.getId());
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
        assertThrows(CommonException.class, () -> gifthubService.addToCart(member.getId(), notExistsItemId, 1L));
    }
}