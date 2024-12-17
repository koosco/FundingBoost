package practice.fundingboost2.item.order.app.dto;

import practice.fundingboost2.common.dto.IdListDto;

public record CreateOrderRequestDto(Long deliveryId, IdListDto itemIds) {

}
