package practice.fundingboost2.item.item.repo.jpa;

import java.util.Optional;
import org.springframework.data.domain.Pageable;
import practice.fundingboost2.item.item.app.dto.GetItemReviewListResponseDto;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.ui.dto.GetItemDetailResponseDto;
import practice.fundingboost2.item.item.ui.dto.GetItemListResponseDto;

public interface ItemQueryRepository {

    GetItemListResponseDto getItems(String category, Pageable pageable);

    GetItemListResponseDto getLikedItems(Long memberId, Pageable pageable);

    Optional<Item> findItemByIdWithOptions(Long itemId);

    GetItemDetailResponseDto getItemInfo(Long memberId, Long itemId);

    GetItemReviewListResponseDto getReviews(Long itemId, Pageable pageable);
}
