package practice.fundingboost2.item.order.ui;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
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
import practice.fundingboost2.item.order.app.DeliveryService;
import practice.fundingboost2.item.order.app.dto.DeliveryRequestDto;
import practice.fundingboost2.item.order.app.dto.DeliveryResponseDto;
import practice.fundingboost2.item.order.app.dto.GetDeliveryListResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @Operation(summary = "배송지 목록 조회", description = "회원의 배송지 목록을 조회합니다.")
    @GetMapping
    public ResponseDto<GetDeliveryListResponseDto> getDeliveries(
        @Auth
        Long memberId) {
        return ResponseDto.ok(deliveryService.getDeliveries(memberId));
    }

    @Operation(summary = "배송지 생성", description = "배송지를 생성합니다.")
    @PostMapping
    public ResponseDto<CommonSuccessDto> createDelivery(
        @Auth
        Long memberId,

        @RequestBody
        @Validated
        DeliveryRequestDto dto) {
        return ResponseDto.ok(deliveryService.createDelivery(memberId, dto));
    }

    @Operation(summary = "배송지 삭제", description = "배송지를 삭제합니다.")
    @DeleteMapping("/{delivery_id}")
    public ResponseDto<CommonSuccessDto> deleteDelivery(
        @Auth
        Long memberId,

        @PathVariable("delivery_id")
        Long deliveryId) {
        return ResponseDto.ok(deliveryService.deleteDelivery(memberId, deliveryId));
    }

    @Operation(summary = "배송지 수정", description = "배송지를 수정합니다.")
    @PatchMapping("/{delivery_id}")
    public ResponseDto<DeliveryResponseDto> updateDelivery(
        @Auth
        Long memberId,

        @PathVariable("delivery_id")
        Long deliveryId,

        @RequestBody
        @Validated
        DeliveryRequestDto dto) {
        return ResponseDto.ok(deliveryService.updateDelivery(memberId, deliveryId, dto));
    }
}
