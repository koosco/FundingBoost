package practice.fundingboost2.order.ui;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.common.dto.ResponseDto;
import practice.fundingboost2.config.security.annotation.Auth;
import practice.fundingboost2.order.app.DeliveryService;
import practice.fundingboost2.order.app.dto.CreateDeliveryRequestDto;
import practice.fundingboost2.order.app.dto.DeliveryDto;
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

    @PostMapping
    public ResponseDto<CommonSuccessDto> createDelivery(@Auth Long memberId, @Valid @RequestBody CreateDeliveryRequestDto dto) {
        return ResponseDto.ok(deliveryService.createDelivery(memberId, dto));
    }

    @DeleteMapping("/{delivery_id}")
    public ResponseDto<CommonSuccessDto> deleteDelivery(@Auth Long memberId, @PathVariable("delivery_id") Long deliveryId) {
        return ResponseDto.ok(deliveryService.deleteDelivery(memberId, deliveryId));
    }
}
