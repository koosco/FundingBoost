package practice.fundingboost2.item.item.repo.querydsl;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import practice.fundingboost2.item.item.ui.dto.GetItemDetailResponseDto;
import practice.fundingboost2.item.item.ui.dto.GetItemListResponseDto;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.ui.dto.GetItemResponseDto;

public interface ItemQueryRepository {

    Page<GetItemResponseDto> getItems(String category, Pageable pageable);

    GetItemListResponseDto getLikedItems(Long memberId, Pageable pageable);

    Optional<Item> findItemByIdWithOptions(Long itemId);

    GetItemDetailResponseDto getItemInfo(Long memberId, Long itemId);
}
