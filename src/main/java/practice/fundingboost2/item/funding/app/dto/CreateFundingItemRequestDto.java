package practice.fundingboost2.item.funding.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

@Schema(description = "펀딩 아이템 생성 요청 Dto")
public record CreateFundingItemRequestDto(

    @Schema(
        description = "펀딩 ID",
        example = "1"
    )
    @NotNull
    Long itemId,

    @Schema(
        description = "상품 옵션 ID",
        example = "1"
    )
    @NotNull
    Long optionId,

    @Schema(
        description = "펀딩 아이템 등록 순서",
        example = "1"
    )
    @NotNull
    @Range(min = 1, max = 5)
    Integer sequence) {

}
