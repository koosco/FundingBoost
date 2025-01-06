package practice.fundingboost2.item.item.repo.querydsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import practice.fundingboost2.item.item.ui.dto.GetItemDetailResponseDto;
import practice.fundingboost2.item.item.ui.dto.GetItemResponseDto;

public interface ItemQueryRepository {

    Page<GetItemResponseDto> getItems(String category, Pageable pageable);

    Page<GetItemResponseDto> getLikedItems(Long memberId, Pageable pageable);

    GetItemDetailResponseDto getItemInfo(Long memberId, Long itemId);
}
