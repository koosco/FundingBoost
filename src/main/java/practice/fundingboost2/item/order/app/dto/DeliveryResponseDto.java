package practice.fundingboost2.item.order.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import practice.fundingboost2.item.order.repo.entity.Delivery;

@Schema(description = "배송 응답 Dto")
public record DeliveryResponseDto(
    @Schema(
        description = "배송 ID",
        example = "1"
    )
    @NotNull
    Long deliveryId,

    @Schema(
        description = "받는 사람",
        example = "홍길동"
    )
    @NotBlank
    String userName,

    @Schema(
        description = "주소",
        example = "서울시 강남구"
    )
    @NotBlank
    String address,

    @Schema(
        description = "전화번호",
        example = "010-1234-5678"
    )
    @NotBlank
    String phoneNumber) {

    public DeliveryResponseDto(Delivery delivery) {
        this(delivery.getId(), delivery.getUserName(), delivery.getAddress(), delivery.getPhoneNumber());
    }
}
