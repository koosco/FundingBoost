package practice.fundingboost2.item.funding.repo;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import practice.fundingboost2.item.funding.app.dto.GetFundingResponseDto;
import practice.fundingboost2.item.funding.app.interfaces.FundingRepository;
import practice.fundingboost2.item.funding.repo.entity.Funding;
import practice.fundingboost2.item.funding.repo.jpa.JpaFundingRepository;
import practice.fundingboost2.item.funding.repo.querydsl.FundingQueryRepository;

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
    public Page<GetFundingResponseDto> findFundings(Long memberId, Pageable pageable) {
        return fundingQueryRepository.findFundings(memberId, pageable);
    }

    @Override
    public List<Funding> findAllByMemberId(Long memberId) {
        return jpaFundingRepository.findAllByMemberId(memberId);
    }

    @Override
    public List<Funding> saveAll(List<Funding> fundings) {
        return jpaFundingRepository.saveAll(fundings);
    }
}
