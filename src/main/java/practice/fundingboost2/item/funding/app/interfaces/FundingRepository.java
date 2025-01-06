package practice.fundingboost2.item.funding.app.interfaces;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import practice.fundingboost2.item.funding.app.dto.GetFundingResponseDto;
import practice.fundingboost2.item.funding.repo.entity.Funding;

public interface FundingRepository {

    Funding save(Funding funding);

    Funding findById(Long fundingId);

    Funding concurrentFindFunding(Long fundingId);

    Page<GetFundingResponseDto> findFundings(Long memberId, Pageable pageable);

    List<Funding> findAllByMemberId(Long memberId);

    List<Funding> saveAll(List<Funding> fundings);
}
