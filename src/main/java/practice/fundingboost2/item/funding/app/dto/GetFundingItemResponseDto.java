package practice.fundingboost2.item.funding.app.dto;

import practice.fundingboost2.item.funding.repo.entity.FundingItem;

public record GetFundingItemResponseDto(
    Long fundingItemId,
    Integer sequence,
    String itemName,
    String itemImageUrl
) {

    public static GetFundingItemResponseDto from(FundingItem fundingItem) {
        return new GetFundingItemResponseDto(
            fundingItem.getId(),
            fundingItem.getSequence(),
            fundingItem.getItem().getName(),
            fundingItem.getItem().getImageUrl());
    }

}
