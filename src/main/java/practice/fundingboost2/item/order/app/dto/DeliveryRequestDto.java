package practice.fundingboost2.item.order.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DeliveryRequestDto(
    @NotBlank
    String userName,

    @NotBlank
    String address,

    @Size(max = 11)
    @NotBlank
    String phoneNumber) {

}
