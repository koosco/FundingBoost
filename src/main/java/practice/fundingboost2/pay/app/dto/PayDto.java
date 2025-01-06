package practice.fundingboost2.pay.app.dto;

public record PayDto(
    String merchantUid,
    String impUid,
    String buyerName,
    String buyerEmail,
    String phoneNumber,
    String address,
    String postcode,
    Integer amount,
    String productName
) {

}
