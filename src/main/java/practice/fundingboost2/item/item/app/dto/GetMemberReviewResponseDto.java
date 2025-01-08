package practice.fundingboost2.item.item.app.dto;

import java.util.List;
import practice.fundingboost2.item.item.repo.entity.Option;
import practice.fundingboost2.item.item.repo.entity.Review;

public record GetMemberReviewResponseDto(
        Long reviewId,
        String itemName,
        Integer score,
        String optionName,
        String content,
        String itemImageUrl,
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
