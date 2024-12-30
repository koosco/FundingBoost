package practice.fundingboost2.item.funding.repo;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import practice.fundingboost2.item.funding.app.dto.GetFundingListResponseDto;
import practice.fundingboost2.item.funding.app.interfaces.FundingRepository;
import practice.fundingboost2.item.funding.repo.entity.Funding;
import practice.fundingboost2.item.funding.repo.jpa.FundingQueryRepository;
import practice.fundingboost2.item.funding.repo.jpa.JpaFundingRepository;

@Repository
@RequiredArgsConstructor
public class FundingRepositoryImpl implements FundingRepository {

    private final JpaFundingRepository jpaFundingRepository;
    private final FundingQueryRepository fundingQueryRepository;

    @Override
    public void save(Funding funding) {
        jpaFundingRepository.save(funding);
    }

    @Override
    public Funding findFunding(Long fundingId) {
        return jpaFundingRepository.findById(fundingId)
            .orElseThrow();
    }

    @Override
    public GetFundingListResponseDto findFundings(Long memberId, Pageable pageable) {
        return fundingQueryRepository.findFundings(memberId, pageable);
    }
}
