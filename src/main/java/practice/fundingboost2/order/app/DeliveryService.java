package practice.fundingboost2.order.app;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.common.exception.ErrorCode;
import practice.fundingboost2.member.app.MemberService;
import practice.fundingboost2.order.app.dto.GetDeliveryListResponseDto;
import practice.fundingboost2.order.repo.DeliveryRepository;
import practice.fundingboost2.order.repo.entity.Delivery;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final MemberService memberService;

    public GetDeliveryListResponseDto getDeliveries(Long memberId) {
        if (!memberService.existsById(memberId)) {
            throw new CommonException(ErrorCode.NOT_FOUND_LOGIN_USER);
        }
        List<Delivery> deliveryAddresses = deliveryRepository.findAll_ByMemberId(memberId);

        return GetDeliveryListResponseDto.from(deliveryAddresses);
    }
}
