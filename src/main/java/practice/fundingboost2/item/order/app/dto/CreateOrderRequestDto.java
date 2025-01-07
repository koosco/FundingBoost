package practice.fundingboost2.item.order.app.dto;

import java.util.List;
import practice.fundingboost2.item.item.app.dto.OrderItemRequestDto;

public record CreateOrderRequestDto(Long deliveryId, List<OrderItemRequestDto> orderItemListRequestDto) {

}
