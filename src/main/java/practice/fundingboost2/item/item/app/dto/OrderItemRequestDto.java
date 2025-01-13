package practice.fundingboost2.item.item.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "주문 상품 요청 Dto")
public record OrderItemRequestDto(

    @Schema(
        description = "상품 ID",
        example = "1")
    @NotNull
    Long itemId,

    @Schema(
        description = "옵션 ID",
        example = "1")
    @NotNull
    Long optionId,

    @Schema(
        description = "수량",
        example = "1")
    @Min(1)
    int quantity) {
}