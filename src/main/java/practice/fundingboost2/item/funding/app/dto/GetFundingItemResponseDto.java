package practice.fundingboost2.item.funding.app.dto;

import practice.fundingboost2.item.funding.repo.entity.FundingItem;

public record GetFundingItemResponseDto(Long itemId, String itemName, String itemImageUrl, String itemOption, int itemPrice) {
    public static GetFundingItemResponseDto from(FundingItem fundingItem){
        return new GetFundingItemResponseDto(fundingItem.getItem().getId(), fundingItem.getItem().getName(), fundingItem.getItem().getImageUrl(),
                fundingItem.getOption().getName(), fundingItem.getItem().getPrice());
    }
}
