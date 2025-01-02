package practice.fundingboost2.item.gifthub.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import practice.fundingboost2.item.gifthub.ui.dto.GetGifthubResponseDto;

public interface GifthubQueryRepository {

    Page<GetGifthubResponseDto> getAllGifthub(Long memberId, Pageable pageable);

}
