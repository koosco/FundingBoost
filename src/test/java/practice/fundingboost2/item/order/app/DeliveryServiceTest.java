package practice.fundingboost2.item.order.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.item.order.app.dto.DeliveryRequestDto;
import practice.fundingboost2.item.order.app.dto.DeliveryResponseDto;
import practice.fundingboost2.item.order.app.dto.GetDeliveryListResponseDto;
import practice.fundingboost2.item.order.repo.DeliveryRepository;
import practice.fundingboost2.item.order.repo.entity.Delivery;
import practice.fundingboost2.member.repo.MemberRepository;
import practice.fundingboost2.member.repo.entity.Member;

@SpringBootTest
class DeliveryServiceTest {

    @Autowired
    DeliveryService deliveryService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    DeliveryRepository deliveryRepository;

    Member member;
    List<Delivery> deliveries = new ArrayList<>();
    static final int DELIVERY_SIZE = 5;

    @BeforeEach
    void init() {
        member = new Member("member1", "password", "nickname", "email");
        member = memberRepository.save(member);

        for (int i = 0; i < DELIVERY_SIZE; i++) {
            Delivery delivery = deliveryRepository.save(
                new Delivery("address" + i, "receiver" + i, "010-1234-5678", member));
            deliveries.add(delivery);
        }
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
        Member member2 = memberRepository.save(new Member("member2", "password", "nickname", "email"));

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
        assertThatThrownBy(() -> deliveryService.getDeliveries(1_000_000_000L))
            .isInstanceOf(CommonException.class);
    }

    @Test
    void givenCreateMember_whenCreateDtoGiven_thenCreateNewDelivery() {
        // given
        Member member2 = memberRepository.save(new Member("member2", "password", "nickname", "email"));
        DeliveryRequestDto dto = new DeliveryRequestDto("receiver", "address",
            "010-1234-5678");
        // when
        CommonSuccessDto responseDto = deliveryService.createDelivery(member2.getId(), dto);

        // then
        List<Delivery> deliveries = deliveryRepository.findAll_ByMemberId(member2.getId());
        assertThat(responseDto.isSuccess()).isTrue();
    }

    @Test
    void givenDelivery_whenDeliveryDelete_thenDeleteDelivery() {
        // given
        Delivery delivery = deliveries.getFirst();

        // when
        CommonSuccessDto responseDto = deliveryService.deleteDelivery(member.getId(), delivery.getId());
        Optional<Delivery> findDelivery = deliveryRepository.findById(delivery.getId());

        // then
        assertThat(responseDto.isSuccess()).isTrue();
        assertThat(findDelivery).isEmpty();
    }

    @Test
    void givenDelivery_whenOtherCall_thenThrowException() {
        // given
        Member other = memberRepository.save(new Member("member2", "password", "nickname", "email"));
        Delivery delivery = deliveries.getFirst();

        // when
        // then
        assertThatThrownBy(() -> deliveryService.deleteDelivery(other.getId(), delivery.getId()))
            .isInstanceOf(CommonException.class);
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
        Member other = memberRepository.save(new Member("member2", "password", "nickname", "email"));
        Delivery delivery = deliveries.getFirst();
        DeliveryRequestDto dto = new DeliveryRequestDto("receiver", "address", "010-1234-5678");

        // when
        // then
        assertThatThrownBy(() -> deliveryService.updateDelivery(other.getId(), delivery.getId(), dto))
            .isInstanceOf(CommonException.class);
    }
}