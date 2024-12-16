package practice.fundingboost2.order.app.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateDeliveryRequestDto(@NotBlank String userName, @NotBlank String address, @NotBlank String phoneNumber) {

}
