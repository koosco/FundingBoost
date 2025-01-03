package practice.fundingboost2.item.funding.repo.jpa;

import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import practice.fundingboost2.item.funding.repo.entity.Funding;

public interface JpaFundingRepository extends JpaRepository<Funding, Long> {

    List<Funding> findAllByMemberId(Long memberId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select f from Funding f where f.id = :funding_id")
    Optional<Funding> findByIdWithLock(@Param("funding_id") Long fundingId);
}
