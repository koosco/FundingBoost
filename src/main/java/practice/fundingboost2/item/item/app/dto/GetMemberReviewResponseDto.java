package practice.fundingboost2.item.item.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import practice.fundingboost2.item.item.repo.entity.Review;

@Schema(description = "회원 리뷰 조회 응답 Dto")
public record GetMemberReviewResponseDto(

    @Schema(
        description = "리뷰 ID",
        example = "1")
    @NotNull
    Long reviewId,

    @Schema(
        description = "상품 이름",
        example = "아이템1")
        @NotBlank
    String itemName,

    @Schema(
        description = "별점",
        example = "5")
    @NotNull
    Integer score,

    @Schema(
        description = "옵션 이름",
        example = "옵션1")
        @Nullable
    String optionName,

    @Schema(
        description = "리뷰 내용",
        example = "또 구매하고 싶어요")
    @Nullable
    String content,

    @Schema(
        description = "상품 이미지 URL",
        example = "http://localhost:8080/image/item1.jpg")
    @Nullable
    String itemImageUrl,

    @Schema(
        description = "리뷰 이미지 URL",
        example = "http://localhost:8080/image/review1.jpg")
    @Nullable
    List<String> reviewImages
) {

    public static GetMemberReviewResponseDto from(Review review) {
        return new GetMemberReviewResponseDto(
            review.getId(),
            review.getItem().getName(),
            review.getScore(),
            review.getOptionName(),
            review.getContent(),
            review.getItem().getImageUrl(),
            review.getReviewImages()
        );
    }
}
