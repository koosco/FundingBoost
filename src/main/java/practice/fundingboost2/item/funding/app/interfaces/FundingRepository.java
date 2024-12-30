package practice.fundingboost2.item.funding.app.interfaces;

import practice.fundingboost2.item.funding.repo.entity.Funding;
import practice.fundingboost2.item.funding.app.dto.GetFundingListResponseDto;

public interface FundingRepository {

    void save(Funding funding);

    Funding findFunding(Long fundingId);

    GetFundingListResponseDto findFundings(Long memberId);
}
