package practice.fundingboost2.item.gifthub.app.dto;

public record GetGifthubResponseDto(
    Long itemId,
    String itemName,
    String itemImage,
    String option,
    Integer quantity) {

}
