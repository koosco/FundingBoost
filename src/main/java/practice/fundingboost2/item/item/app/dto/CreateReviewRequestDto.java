package practice.fundingboost2.item.item.app.dto;

import jakarta.validation.constraints.NotNull;

public record CreateReviewRequestDto(
    @NotNull
    Long itemId,

    @NotNull
    Long orderId,

    String content,

    @NotNull
    Integer score
) {

}
