package practice.fundingboost2.pay.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.fundingboost2.pay.repo.entity.Pay;

public interface PayRepository extends JpaRepository<Pay, Long> {

}
