package practice.fundingboost2.item.order.repo.entity;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.repo.entity.Option;
import practice.fundingboost2.member.repo.entity.Member;

@SpringBootTest
@Transactional
class OrderTest {

    Member member;

    Item item;

    Delivery delivery;

    List<Option> options = new ArrayList<>();

    final int OPTION_SIZE = 5;

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void init() {
        member = new Member("email", "name", "image", "phoneNumber");
        em.persist(member);
        item = new Item("name", 10_000, "item_image_url", "brand", "category");
        em.persist(item);

        for (int i = 0; i < OPTION_SIZE; i++) {
            Option option = new Option(item,"option"+i,i);
            options.add(option);
            em.persist(option);
        }

        delivery = new Delivery("user1", "address1", "phoneNumber", member);
        em.persist(delivery);

        em.flush();
    }

    @Test
    void givenDefault_whenCreate_thenCreateNewOrder() {
        // given
        // when
        Order order = new Order(member, item, delivery, item.getOptions().getFirst().getName());
        // then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.PENDING_PAYMENT);
        assertThat(order.getDelivery()).isEqualTo(delivery);
        assertThat(order.getItem()).isEqualTo(item);
        assertThat(order.getMember()).isEqualTo(member);
        assertThat(order.getOptionName()).isEqualTo(item.getOptions().getFirst().getName());
    }
}