package practice.fundingboost2.item.order.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "배송 요청 Dto")
public record DeliveryRequestDto(
    @NotBlank
    String userName,

    @NotBlank
    String address,

    @Size(max = 11)
    @NotBlank
    String phoneNumber) {

}
