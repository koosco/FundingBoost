package practice.fundingboost2.item.item.repo.jpa;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import practice.fundingboost2.item.item.repo.entity.Item;

public interface JpaItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByIdIn(List<Long> ids);
}
