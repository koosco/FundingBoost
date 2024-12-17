package practice.fundingboost2.item.order.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.fundingboost2.item.order.repo.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
