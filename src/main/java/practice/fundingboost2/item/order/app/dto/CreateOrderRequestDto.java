package practice.fundingboost2.item.order.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import practice.fundingboost2.item.item.app.dto.OrderItemRequestDto;

/**
 * TODO deliverId 대신 delivery 정보 Dto로 받도록 수정
 * @param deliveryId
 * @param orderItemListRequestDto
 */
@Schema(description = "주문 생성 요청 Dto")
public record CreateOrderRequestDto(

    @Schema(
        description = "배송 ID",
        example = "1"
    )
    @NotNull
    Long deliveryId,

    @Schema(
        description = "주문 상품 목록"
    )
    @NotEmpty
    List<OrderItemRequestDto> orderItemListRequestDto) {

}
