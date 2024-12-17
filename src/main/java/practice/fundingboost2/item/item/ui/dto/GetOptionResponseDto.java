package practice.fundingboost2.item.item.ui.dto;

import practice.fundingboost2.item.item.repo.entity.Option;

public record GetOptionResponseDto(Long optionId, String optionName, int quantity) {

        public static GetOptionResponseDto from(Option option) {
            return new GetOptionResponseDto(option.getId(), option.getName(), option.getQuantity());
        }

}
