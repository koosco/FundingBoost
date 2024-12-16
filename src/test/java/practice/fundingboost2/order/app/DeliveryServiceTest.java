package practice.fundingboost2.order.app;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.member.repo.entity.Member;
import practice.fundingboost2.order.app.dto.GetDeliveryListResponseDto;
import practice.fundingboost2.order.repo.entity.Delivery;

@Transactional
@SpringBootTest
class DeliveryServiceTest {

    @Autowired
    DeliveryService deliveryService;

    @PersistenceContext
    EntityManager em;

    Member member;
    List<Delivery> deliveries = new ArrayList<>();
    static final int DELIVERY_SIZE = 5;

    @BeforeEach
    void init() {
        member = new Member("member1", "password", "nickname", "email");
        em.persist(member);
        em.flush();

        for (int i = 0; i < DELIVERY_SIZE; i++) {
            Delivery delivery = new Delivery("address" + i, "receiver" + i, "010-1234-5678", member);
            em.persist(delivery);
            deliveries.add(delivery);
        }
        em.flush();
    }
    
    @Test
    void givenCreateDeliveries_whenCalled_thenReturnMembersDeliveryList() {
        // given
        // when
        GetDeliveryListResponseDto dto = deliveryService.getDeliveries(member.getId());
        // then
        assertThat(dto.addresses()).hasSize(DELIVERY_SIZE);
    }

    @Test
    void givenNoDeliveries_whenCalled_thenReturnEmptyDto() {
        // given
        Member member2 = new Member("member2", "password", "nickname", "email");

        // when
        GetDeliveryListResponseDto dto = deliveryService.getDeliveries(member2.getId());

        // then
        assertThat(dto.addresses()).isEmpty();
    }

    @Test
    void givenNotExistMemberId_whenCalled_thenThrowException() {
        // given
        // when
        // then
        assertThrows(CommonException.class, () -> deliveryService.getDeliveries(1L));
    }
}