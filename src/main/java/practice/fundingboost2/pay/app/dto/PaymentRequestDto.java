package practice.fundingboost2.pay.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PaymentRequestDto(
    @NotBlank
    String merchantUid,

    @NotBlank
    String impUid,

    @NotBlank
    String buyerName,

    @NotBlank
    String buyerEmail,

    @NotBlank
    String phoneNumber,

    @NotBlank
    String address,

    @NotBlank
    String postcode,

    @NotNull
    Integer amount,

    @NotBlank
    String productName
) {

}
