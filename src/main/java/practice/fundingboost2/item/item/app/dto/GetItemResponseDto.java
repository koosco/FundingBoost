package practice.fundingboost2.item.item.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import practice.fundingboost2.item.funding.repo.entity.FundingItem;
import practice.fundingboost2.item.item.repo.entity.Item;

@Schema(description = "상품 조회 응답 Dto")
public record GetItemResponseDto(

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
    String itemImageUrl,

    @Schema(
        description = "상품 옵션",
        example = "옵션1")
    @Nullable
    String itemOption,
    int itemPrice) {

    public static GetItemResponseDto from(FundingItem fundingItem) {
        Item item = fundingItem.getItem();
        return new GetItemResponseDto(item.getId(), item.getName(), item.getImageUrl(),
            fundingItem.getOption().getName(), item.getPrice());
    }
}
