package practice.fundingboost2.item.item.app.dto;

import java.time.LocalDateTime;
import java.util.List;
import practice.fundingboost2.item.item.repo.entity.Review;

public record GetReviewResponseDto(
    Long memberId,
    Long reviewId,
    String memberName,
    String memberImageUrl,
    int reviewScore,
    LocalDateTime reviewCreatedAt,
    String reviewContent,
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
