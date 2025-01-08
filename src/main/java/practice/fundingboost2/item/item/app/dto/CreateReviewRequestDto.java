package practice.fundingboost2.item.item.app.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public record CreateReviewRequestDto(
    @NotNull
    Long itemId,

    @NotNull
    Long orderId,

    String content,

    @NotNull
    @Range(min = 1, max = 5)
    Integer score
) {

}
