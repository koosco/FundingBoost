package practice.fundingboost2.item.funding.repo.jpa;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import practice.fundingboost2.item.funding.repo.entity.Contributor;

public interface ContributorRepository extends JpaRepository<Contributor, Long> {

    List<Contributor> findAll_ByFundingId(Long fundingId);
}
