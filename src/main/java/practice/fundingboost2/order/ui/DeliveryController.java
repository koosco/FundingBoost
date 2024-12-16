package practice.fundingboost2.order.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.fundingboost2.common.dto.ResponseDto;
import practice.fundingboost2.config.security.annotation.Auth;
import practice.fundingboost2.order.app.DeliveryService;
import practice.fundingboost2.order.app.dto.GetDeliveryListResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping
    public ResponseDto<GetDeliveryListResponseDto> getDeliveries(@Auth Long memberId) {
        return ResponseDto.ok(deliveryService.getDeliveries(memberId));
    }
}
