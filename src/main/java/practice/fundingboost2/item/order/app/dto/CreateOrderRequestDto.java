package practice.fundingboost2.item.order.app.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import practice.fundingboost2.item.item.app.dto.OrderItemRequestDto;

public record CreateOrderRequestDto(
    @NotNull
    Long deliveryId,

    @NotEmpty
    List<OrderItemRequestDto> orderItemListRequestDto) {

}
