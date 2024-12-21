package practice.fundingboost2.item.gifthub.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
public class GifthubServiceUpdateTest {

    @Autowired
    GifthubService gifthubService;

    @PersistenceContext
    EntityManager em;

    Item item;

    Member member;

    Option option;

    @BeforeEach
    void init() {
        item = new Item("item", 1000, "url", "brand", "category");
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
    void givenGifthub_whenUpdateQuantityToThree_thenQuantityUpdate() {
        // given
        gifthubService.addToCart(member.getId(), item.getId(), option.getId());

        // when
        CommonSuccessDto dto = gifthubService.updateCart(member.getId(), item.getId(), option.getId(), 3);

        // then
        assertTrue(dto.isSuccess());
        Gifthub findGifthub = em.createQuery(
                "select g from Gifthub g where g.id=:id", Gifthub.class)
            .setParameter("id", new GifthubId(member.getId(), item.getId(), option.getId()))
            .getSingleResult();
        assertThat(findGifthub.getQuantity()).isEqualTo(3);
    }

    @Test
    void givenGifthub_whenUpdateWithQuantityZERO_thenThrowException() {
        // given
        gifthubService.addToCart(member.getId(), item.getId(), option.getId());
        // when
        // then
        assertThrows(
            CommonException.class, () -> gifthubService.updateCart(member.getId(), item.getId(), option.getId(), 0));
    }

    @Test
    void givenGifthub_whenUpdateWithQuantityNegative_thenThrowException() {
        // given
        gifthubService.addToCart(member.getId(), item.getId(), option.getId());
        // when
        // then
        assertThrows(CommonException.class, () -> gifthubService.updateCart(member.getId(), item.getId(), option.getId(), -1));
    }
}
