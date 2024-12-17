package practice.fundingboost2.item.order.repo.entity;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.member.repo.entity.Member;

@SpringBootTest
@Transactional
class OrderTest {

    Member member;

    Item item;

    Delivery delivery;

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void init() {
        member = new Member("email", "name", "image", "phoneNumber");
        em.persist(member);
        item = new Item("name", 10_000, "item_image_url", "brand", "category", 0, 0);
        em.persist(item);
        delivery = new Delivery("user1", "address1", "phoneNumber", member);
        em.persist(delivery);

        em.flush();
    }

    @Test
    void givenDefault_whenCreate_thenCreateNewOrder() {
        // given
        // when
        Order order = new Order(member, item, delivery);
        // then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.PENDING_PAYMENT);
        assertThat(order.getDelivery()).isEqualTo(delivery);
        assertThat(order.getItem()).isEqualTo(item);
        assertThat(order.getMember()).isEqualTo(member);
    }
}