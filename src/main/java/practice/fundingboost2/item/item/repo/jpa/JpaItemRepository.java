package practice.fundingboost2.item.item.repo.jpa;

import jakarta.persistence.LockModeType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import practice.fundingboost2.item.item.repo.entity.Item;

public interface JpaItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByIdIn(List<Long> ids);

    Boolean existsByIdAndOptions_Id(Long itemId, Long optionId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select i from Item i where i.id = :item_id")
    Item findByIdUsingLock(@Param("item_id") Long itemId);
}
