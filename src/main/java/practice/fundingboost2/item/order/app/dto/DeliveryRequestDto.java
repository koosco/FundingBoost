package practice.fundingboost2.item.order.app.dto;

import jakarta.validation.constraints.NotBlank;

public record DeliveryRequestDto(@NotBlank String userName, @NotBlank String address, @NotBlank String phoneNumber) {

}
