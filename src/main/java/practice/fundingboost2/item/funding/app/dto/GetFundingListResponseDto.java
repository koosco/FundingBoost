package practice.fundingboost2.item.funding.app.dto;

import java.util.List;
import practice.fundingboost2.item.funding.repo.entity.Funding;

public record GetFundingListResponseDto(
    List<GetFundingResponseDto> fundings
) {

    public static GetFundingListResponseDto from(List<Funding> fundings) {
        return new GetFundingListResponseDto(fundings.stream()
            .map(GetFundingResponseDto::from)
            .toList());
    }
}
