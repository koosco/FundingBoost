package practice.fundingboost2.item.funding.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.fundingboost2.item.funding.repo.entity.Funding;

public interface FundingRepository extends JpaRepository<Funding, Long> {

}
