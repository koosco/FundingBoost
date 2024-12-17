package practice.fundingboost2.order.app;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.item.order.app.DeliveryService;
import practice.fundingboost2.member.repo.entity.Member;
import practice.fundingboost2.item.order.app.dto.DeliveryRequestDto;
import practice.fundingboost2.item.order.app.dto.DeliveryResponseDto;
import practice.fundingboost2.item.order.app.dto.GetDeliveryListResponseDto;
import practice.fundingboost2.item.order.repo.entity.Delivery;

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
        em.persist(member2);
        em.flush();

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

    @Test
    void givenCreateMember_whenCreateDtoGiven_thenCreateNewDelivery() {
        // given
        Member member2 = new Member("member2", "password", "nickname", "email");
        em.persist(member2);
        em.flush();
        DeliveryRequestDto dto = new DeliveryRequestDto("receiver", "address",
            "010-1234-5678");
        // when
        CommonSuccessDto responseDto = deliveryService.createDelivery(member2.getId(), dto);
        Delivery delivery = em.createQuery("select d from Delivery d join Member m on d.member.id = m.id where m.id = :memberId",
                Delivery.class)
            .setParameter("memberId", member2.getId())
            .getSingleResult();

        // then
        assertThat(responseDto.isSuccess()).isTrue();
        assertThat(delivery.getMember()).isEqualTo(member2);
    }

    @Test
    void givenDelivery_whenDeliveryDelete_thenDeleteDelivery() {
        // given
        Delivery delivery = deliveries.getFirst();

        // when
        CommonSuccessDto responseDto = deliveryService.deleteDelivery(member.getId(), delivery.getId());

        // then
        assertThat(responseDto.isSuccess()).isTrue();
        assertThrows(NoResultException.class,
            () -> em.createQuery("select d from Delivery d where d.id=:deliveryId", Delivery.class)
                .setParameter("deliveryId", delivery.getId())
                .getSingleResult());
    }

    @Test
    void givenDelivery_whenOtherCall_thenThrowException() {
        // given
        Member other = new Member("member2", "password", "nickname", "email");
        em.persist(other);
        em.flush();
        Delivery delivery = deliveries.getFirst();

        // when
        // then
        assertThrows(CommonException.class, () -> deliveryService.deleteDelivery(other.getId(), delivery.getId()));
    }

    @Test
    void givenDelivery_whenDtoGiven_thenUpdate() {
        // given
        Delivery delivery = deliveries.getFirst();
        DeliveryRequestDto dto = new DeliveryRequestDto("newReceiver", "newAddress", "010-1234-5678");
        // when
        DeliveryResponseDto responseDto = deliveryService.updateDelivery(member.getId(), delivery.getId(), dto);
        // then
        assertThat(responseDto.deliveryId()).isEqualTo(delivery.getId());
        assertThat(responseDto.userName()).isEqualTo(dto.userName());
        assertThat(responseDto.address()).isEqualTo(dto.address());
        assertThat(responseDto.phoneNumber()).isEqualTo(dto.phoneNumber());
    }

    @Test
    void givenDelivery_whenUsernameGivenElseNull_thenUpdateUsername() {
        // given
        Delivery delivery = deliveries.getFirst();
        DeliveryRequestDto dto = new DeliveryRequestDto("newReceiver", null, null);

        // when
        DeliveryResponseDto responseDto = deliveryService.updateDelivery(member.getId(), delivery.getId(), dto);
        // then
        assertThat(responseDto.deliveryId()).isEqualTo(delivery.getId());
        assertThat(responseDto.userName()).isEqualTo(dto.userName());
        assertThat(responseDto.address()).isEqualTo(delivery.getAddress());
        assertThat(responseDto.phoneNumber()).isEqualTo(delivery.getPhoneNumber());
    }

    @Test
    void givenDelivery_whenAddressGiven_thenUpdateAddress() {
        // given
        Delivery delivery = deliveries.getFirst();
        DeliveryRequestDto dto = new DeliveryRequestDto(null, "newAddress", null);

        // when
        DeliveryResponseDto responseDto = deliveryService.updateDelivery(member.getId(), delivery.getId(), dto);
        // then
        assertThat(responseDto.deliveryId()).isEqualTo(delivery.getId());
        assertThat(responseDto.userName()).isEqualTo(delivery.getUserName());
        assertThat(responseDto.address()).isEqualTo(dto.address());
        assertThat(responseDto.phoneNumber()).isEqualTo(delivery.getPhoneNumber());
    }

    @Test
    void givenDelivery_whenPhoneNumber_thenUpdatePhoneNumber() {
        // given
        Delivery delivery = deliveries.getFirst();
        DeliveryRequestDto dto = new DeliveryRequestDto(null, null, "010-1234-5678");

        // when
        DeliveryResponseDto responseDto = deliveryService.updateDelivery(member.getId(), delivery.getId(), dto);
        // then
        assertThat(responseDto.deliveryId()).isEqualTo(delivery.getId());
        assertThat(responseDto.userName()).isEqualTo(delivery.getUserName());
        assertThat(responseDto.address()).isEqualTo(delivery.getAddress());
        assertThat(responseDto.phoneNumber()).isEqualTo(dto.phoneNumber());
    }

    @Test
    void givenDelivery_whenEmptyDtoGiven_thenNoUpdate() {
        // given
        Delivery delivery = deliveries.getFirst();
        DeliveryRequestDto dto = new DeliveryRequestDto(null, null, null);

        // when
        DeliveryResponseDto responseDto = deliveryService.updateDelivery(member.getId(), delivery.getId(), dto);
        // then
        assertThat(responseDto.deliveryId()).isEqualTo(delivery.getId());
        assertThat(responseDto.userName()).isEqualTo(delivery.getUserName());
        assertThat(responseDto.address()).isEqualTo(delivery.getAddress());
        assertThat(responseDto.phoneNumber()).isEqualTo(delivery.getPhoneNumber());
    }

    @Test
    void givenDelivery_whenOtherUpdate_thenThrowException() {
        // given
        Member other = new Member("member2", "password", "nickname", "email");
        em.persist(other);
        em.flush();
        Delivery delivery = deliveries.getFirst();
        DeliveryRequestDto dto = new DeliveryRequestDto("receiver", "address", "010-1234-5678");

        // when
        // then
        assertThrows(CommonException.class, () -> deliveryService.updateDelivery(other.getId(), delivery.getId(), dto));
    }
}