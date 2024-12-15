package practice.fundingboost2.item.repo;

import org.springframework.data.domain.Pageable;
import practice.fundingboost2.item.app.dto.GetItemListResponseDto;

public interface ItemQueryRepository {

    GetItemListResponseDto getItems(Pageable pageable);

}
