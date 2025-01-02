package practice.fundingboost2.item.item.app.dto;

import java.time.LocalDateTime;

public record GetItemReviewResponseDto(String memberName, String memberImageUrl, int reviewScore, LocalDateTime reviewCreatedAt, String reviewContent) {
}
