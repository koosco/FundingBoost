package practice.fundingboost2.item.funding.repo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.fundingboost2.item.funding.repo.entity.Funding;

public interface JpaFundingRepository extends JpaRepository<Funding, Long> {

}