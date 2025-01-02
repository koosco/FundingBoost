package practice.fundingboost2.item.funding.repo.querydsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import practice.fundingboost2.item.funding.app.dto.GetFundingResponseDto;

public interface FundingQueryRepository {

    Page<GetFundingResponseDto> findFundings(Long memberId, Pageable pageable);
}
