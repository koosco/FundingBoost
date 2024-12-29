package practice.fundingboost2.item.funding.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import practice.fundingboost2.item.funding.repo.entity.Funding;

public interface FundingRepository extends JpaRepository<Funding, Long> {

    List<Funding> findAllByMemberId(Long memberId);
}
