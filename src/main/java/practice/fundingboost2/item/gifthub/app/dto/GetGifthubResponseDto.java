package practice.fundingboost2.item.gifthub.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "장바구니 응답 Dto")
public record GetGifthubResponseDto(

    @Schema(
        description = "상품 ID",
        example = "1")
    @NotNull
    Long itemId,

    @Schema(
        description = "상품 이름",
        example = "아이템1")
    @NotBlank
    String itemName,

    @Schema(
        description = "상품 이미지 URL",
        example = "http://localhost:8080/image/item1.jpg")
    @Nullable
    String itemImage,

    @Schema(
        description = "상품 가격",
        example = "10000")
    @NotBlank
    String option,

    @Schema(
        description = "상품 수량",
        example = "1")
    @NotNull
    Integer quantity) {

}
