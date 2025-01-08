package practice.fundingboost2.item.item.app.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderItemRequestDto(
    @NotNull
    Long itemId,

    @NotNull
    Long optionId,

    @Min(1)
    int quantity) {
}