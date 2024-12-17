package practice.fundingboost2.item.order.app;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.common.exception.ErrorCode;
import practice.fundingboost2.member.app.MemberService;
import practice.fundingboost2.member.repo.entity.Member;
import practice.fundingboost2.item.order.app.dto.DeliveryRequestDto;
import practice.fundingboost2.item.order.app.dto.DeliveryResponseDto;
import practice.fundingboost2.item.order.app.dto.GetDeliveryListResponseDto;
import practice.fundingboost2.item.order.repo.DeliveryRepository;
import practice.fundingboost2.item.order.repo.entity.Delivery;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final MemberService memberService;

    public Delivery findDelivery(Long deliveryId) {
        return deliveryRepository.findById(deliveryId)
            .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_DELIVERY));
    }

    @Transactional(readOnly = true)
    public GetDeliveryListResponseDto getDeliveries(Long memberId) {
        if (!memberService.existsById(memberId)) {
            throw new CommonException(ErrorCode.NOT_FOUND_LOGIN_USER);
        }

        List<Delivery> deliveryAddresses = deliveryRepository.findAll_ByMemberId(memberId);

        return GetDeliveryListResponseDto.from(deliveryAddresses);
    }

    public CommonSuccessDto createDelivery(Long memberId, DeliveryRequestDto dto) {
        Member member = memberService.findMember(memberId);

        Delivery delivery = new Delivery(dto.userName(), dto.address(), dto.phoneNumber(), member);
        deliveryRepository.save(delivery);

        return CommonSuccessDto.fromEntity(true);
    }

    public CommonSuccessDto deleteDelivery(Long memberId, Long deliveryId) {
        Member member = memberService.findMember(memberId);
        Delivery delivery = findDelivery(deliveryId);
        delivery.validateMember(member);

        deliveryRepository.delete(delivery);

        return CommonSuccessDto.fromEntity(true);
    }

    public DeliveryResponseDto updateDelivery(Long memberId, Long deliveryId, @Valid DeliveryRequestDto dto) {
        Member member = memberService.findMember(memberId);
        Delivery delivery = findDelivery(deliveryId);
        delivery.validateMember(member);

        delivery.update(dto.userName(), dto.address(), dto.phoneNumber());

        return new DeliveryResponseDto(delivery);
    }
}
