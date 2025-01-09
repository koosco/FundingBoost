package practice.fundingboost2.item.order.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import practice.fundingboost2.item.order.repo.entity.Delivery;

/**
 * TODO delivery Dto로 응답하도록 수정
 * @param addresses
 */
@Schema(description = "배송 목록 응답 Dto")
public record GetDeliveryListResponseDto(

    @Schema(description = "배송 목록")
    List<DeliveryResponseDto> addresses) {


    public static GetDeliveryListResponseDto from(List<Delivery> deliveries) {
        return new GetDeliveryListResponseDto(deliveries.stream()
            .map(DeliveryResponseDto::new)
            .toList());
    }
}
