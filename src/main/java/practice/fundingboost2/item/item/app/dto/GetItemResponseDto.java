package practice.fundingboost2.item.item.app.dto;

import practice.fundingboost2.item.funding.repo.entity.FundingItem;
import practice.fundingboost2.item.item.repo.entity.Item;

public record GetItemResponseDto(
    Long itemId,
    String itemName,
    String itemImageUrl,
    String itemOption,
    int itemPrice) {

    public static GetItemResponseDto from(FundingItem fundingItem) {
        Item item = fundingItem.getItem();
        return new GetItemResponseDto(item.getId(), item.getName(), item.getImageUrl(),
            fundingItem.getOption().getName(), item.getPrice());
    }
}
