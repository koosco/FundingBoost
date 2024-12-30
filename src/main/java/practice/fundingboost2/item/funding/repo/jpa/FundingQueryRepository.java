package practice.fundingboost2.item.funding.repo.jpa;

import org.springframework.data.domain.Pageable;
import practice.fundingboost2.item.funding.app.dto.GetFundingListResponseDto;

public interface FundingQueryRepository {

    GetFundingListResponseDto findFundings(Long memberId, Pageable pageable);
}
