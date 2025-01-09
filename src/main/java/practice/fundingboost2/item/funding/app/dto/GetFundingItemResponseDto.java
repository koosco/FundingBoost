package practice.fundingboost2.item.funding.app.dto;

import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import practice.fundingboost2.item.funding.repo.entity.FundingItem;

@Schema(description = "펀딩 상품 조회 응답 Dto")
public record GetFundingItemResponseDto(

    @Schema(
        description = "펀딩 상품 ID",
        example = "1")
    @NotNull
    Long fundingItemId,

    @Schema(
        description = "펀딩 상품 등록 순서",
        example = "1")
    @NotNull
    Integer sequence,

    @Schema(
        description = "상품 이름",
        example = "아이템1")
    @NotBlank
    String itemName,

    @Schema(
        description = "상품 이미지 URL",
        example = "http://localhost:8080/image/item1.jpg")
    @Nullable
    String itemImageUrl
) {

    public static GetFundingItemResponseDto from(FundingItem fundingItem) {
        return new GetFundingItemResponseDto(
            fundingItem.getId(),
            fundingItem.getSequence(),
            fundingItem.getItem().getName(),
            fundingItem.getItem().getImageUrl());
    }

}
