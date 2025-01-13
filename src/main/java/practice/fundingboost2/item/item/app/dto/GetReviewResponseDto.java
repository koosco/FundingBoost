package practice.fundingboost2.item.item.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.validator.constraints.Range;
import practice.fundingboost2.item.item.repo.entity.Review;

@Schema(description = "리뷰 목록 조회 응답 Dto")
public record GetReviewResponseDto(

    @Schema(
        description = "리뷰 ID",
        example = "1")
    @NotNull
    Long reviewId,

    @Schema(
        description = "회원 ID",
        example = "1")
    @NotNull
    Long memberId,

    @Schema(
        description = "회원 이름",
        example = "홍길동")
    @NotBlank
    String memberName,

    @Schema(
        description = "회원 이미지 URL",
        example = "http://localhost:8080/image/member1.jpg")
    @Nullable
    String memberImageUrl,

    @Schema(
        description = "별점",
        example = "5")
    @Range(min = 1, max = 5)
    @NotNull
    int reviewScore,

    @Schema(
        description = "리뷰 생성일",
        example = "2021-08-01T00:00:00")
    @NotNull
    LocalDateTime reviewCreatedAt,

    @Schema(
        description = "리뷰 내용",
        example = "또 구매하고 싶어요")
    String reviewContent,

    @Schema(
        description = "리뷰 이미지 URL",
        example = "http://localhost:8080/image/review1.jpg")
    List<String> reviewImages) {

    public static GetReviewResponseDto from(Review review) {
        return new GetReviewResponseDto(
            review.getMember().getId(),
            review.getId(),
            review.getMember().getNickname(),
            review.getMember().getImageUrl(),
            review.getScore(),
            review.getCreatedAt(),
            review.getContent(),
            review.getReviewImages());
    }
}
