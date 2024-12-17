package practice.fundingboost2.item.order.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import practice.fundingboost2.item.order.repo.entity.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    List<Delivery> findAll_ByMemberId(Long memberId);
}
