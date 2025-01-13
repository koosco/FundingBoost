package practice.fundingboost2.item.item.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

@Schema(description = "리뷰 생성 요청 Dto")
public record CreateReviewRequestDto(

    @Schema(
        description = "상품 ID",
        example = "1"
    )
    @NotNull
    Long itemId,

    @Schema(
        description = "주문 ID",
        example = "1"
    )
    @NotNull
    Long orderId,

    @Schema(
        description = "리뷰 내용",
        example = "또 구매하고 싶어요"
    )
    @Nullable
    String content,

    @Schema(
        description = "별점",
        example = "5"
    )
    @NotNull
    @Range(min = 1, max = 5)
    Integer score
) {

}
