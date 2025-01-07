package practice.fundingboost2.item.order.app.dto;

import java.time.LocalDateTime;
import practice.fundingboost2.item.order.repo.entity.Order;

public record GetOrderHistoryResponseDto(long orderId,
                                         LocalDateTime createdAt,
                                         int price,
                                         String itemImageUrl,
                                         String itemName,
                                         String itemOptionName) {
    public static GetOrderHistoryResponseDto from(Order order){
        return new GetOrderHistoryResponseDto(
                order.getId(),
                order.getCreatedAt(),
                order.getItem().getPrice() * order.getQuantity(),
                order.getItem().getImageUrl(),
                order.getItem().getName(),
                order.getOptionName()
        );
    }
}
