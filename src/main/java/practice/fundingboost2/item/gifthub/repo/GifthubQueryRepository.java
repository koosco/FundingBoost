package practice.fundingboost2.item.gifthub.repo;

import org.springframework.data.domain.Pageable;
import practice.fundingboost2.item.gifthub.ui.dto.GetGifthubListResponseDto;

public interface GifthubQueryRepository {

    GetGifthubListResponseDto getAllGifthub(Long memberId, Pageable pageable);

}
