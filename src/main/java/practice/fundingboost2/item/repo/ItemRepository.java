package practice.fundingboost2.item.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.fundingboost2.item.repo.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
