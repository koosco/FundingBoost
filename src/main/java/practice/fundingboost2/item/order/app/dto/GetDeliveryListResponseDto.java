package practice.fundingboost2.item.order.app.dto;

import java.util.List;
import practice.fundingboost2.item.order.repo.entity.Delivery;

public record GetDeliveryListResponseDto(List<DeliveryResponseDto> addresses) {


    public static GetDeliveryListResponseDto from(List<Delivery> deliveries) {
        return new GetDeliveryListResponseDto(deliveries.stream()
            .map(DeliveryResponseDto::new)
            .toList());
    }
}
