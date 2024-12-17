package practice.fundingboost2.item.item.repo;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.common.exception.ErrorCode;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.repo.jpa.ItemQueryRepository;
import practice.fundingboost2.item.item.repo.jpa.JpaItemRepository;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final JpaItemRepository itemRepository;
    private final ItemQueryRepository itemQueryRepository;

    public boolean existsById(Long itemId) {
        return itemRepository.existsById(itemId);
    }

    public Item findById(Long id) {
        return itemRepository.findById(id)
            .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ITEM));
    }

    public Item findItemByIdWithOptions(Long itemId) {
        return itemQueryRepository.findItemByIdWithOptions(itemId)
            .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ITEM));
    }

    public List<Item> findByIdIn(List<Long> ids) {
        return itemRepository.findByIdIn(ids);
    }
}
