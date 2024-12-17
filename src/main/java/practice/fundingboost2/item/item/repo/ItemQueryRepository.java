package practice.fundingboost2.item.item.repo;

import org.springframework.data.domain.Pageable;
import practice.fundingboost2.item.item.app.dto.GetItemListResponseDto;

public interface ItemQueryRepository {

    GetItemListResponseDto getItems(Pageable pageable);

    GetItemListResponseDto getLikedItems(Long memberId, Pageable pageable);
}
