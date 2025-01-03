package practice.fundingboost2.item.funding.app.dto;

import java.util.List;

public record GetFundingHistoryListResponseDto(
    List<GetFundingHistoryResponseDto> fundingHistoryDtos) {
}
