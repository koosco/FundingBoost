package practice.fundingboost2.item.item.repo;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.common.exception.ErrorCode;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.repo.jpa.JpaItemRepository;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final JpaItemRepository itemRepository;

    public Item findById(Long id) {
        return itemRepository.findById(id)
            .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ITEM));
    }

    public List<Item> findByIdIn(List<Long> ids) {
        return itemRepository.findByIdIn(ids);
    }

    public Boolean existsById(Long itemId, Long optionId) {
        return itemRepository.existsByIdAndOptions_Id(itemId, optionId);
    }

    public Item concurrencyFindItem(Long itemId) {
        return itemRepository.findByIdUsingLock(itemId);
    }

    public Item save(Item item) {
        return itemRepository.save(item);
    }
}
