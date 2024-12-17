package practice.fundingboost2.item.item.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.fundingboost2.item.item.repo.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
