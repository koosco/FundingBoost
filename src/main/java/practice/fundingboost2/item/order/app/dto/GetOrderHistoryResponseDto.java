package practice.fundingboost2.item.order.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import practice.fundingboost2.item.order.repo.entity.Order;

/**
 * TODO 주문 Dto 통합
 */
@Schema(description = "주문 내역 응답 Dto")
public record GetOrderHistoryResponseDto(

    @Schema(
        description = "주문 ID",
        example = "1")
    @NotNull
    Long orderId,

    @Schema(
        description = "주문 일시",
        example = "2021-08-01T00:00:00")
    @NotNull
    LocalDateTime createdAt,

    @Schema(
        description = "주문 금액",
        example = "10000")
    @NotNull
    int price,

    @Schema(
        description = "상품 이미지 URL",
        example = "http://localhost:8080/image/item1.jpg")
    @NotNull
    String itemImageUrl,

    @Schema(
        description = "상품 이름",
        example = "아이템1")
    @NotNull
    String itemName,

    @Schema(
        description = "상품 옵션",
        example = "옵션1")
    @NotNull
    String itemOptionName) {

    public static GetOrderHistoryResponseDto from(Order order) {
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
