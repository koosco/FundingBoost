package practice.fundingboost2.item.funding.repo;

import practice.fundingboost2.item.funding.ui.dto.GetFundingListResponseDto;

public interface FundingQueryRepository {

    GetFundingListResponseDto findFundings(Long memberId);
}
