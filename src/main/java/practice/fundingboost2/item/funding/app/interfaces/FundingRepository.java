package practice.fundingboost2.item.funding.app.interfaces;

import java.util.Arrays;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import practice.fundingboost2.item.funding.app.dto.GetFundingResponseDto;
import practice.fundingboost2.item.funding.repo.entity.Funding;

public interface FundingRepository {

    void save(Funding funding);

    Funding findFunding(Long fundingId);

    Page<GetFundingResponseDto> findFundings(Long memberId, Pageable pageable);

    List<Funding> findAllByMemberId(Long memberId);
}
