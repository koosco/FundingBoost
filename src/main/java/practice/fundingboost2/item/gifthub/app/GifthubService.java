package practice.fundingboost2.item.gifthub.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.common.exception.ErrorCode;
import practice.fundingboost2.item.gifthub.repo.GifthubRepository;
import practice.fundingboost2.item.gifthub.repo.entity.Gifthub;
import practice.fundingboost2.item.gifthub.repo.entity.GifthubId;
import practice.fundingboost2.item.item.app.ItemService;

@Service
@Transactional
@RequiredArgsConstructor
public class GifthubService {

    private final GifthubRepository gifthubRepository;
    private final ItemService itemService;

    public CommonSuccessDto addToCart(Long memberId, Long itemId) {
        validateItem(itemId);

        GifthubId id = new GifthubId(memberId, itemId);

        if (gifthubRepository.existsById(id)) {
            gifthubRepository.updateQuantityById(id);
            return CommonSuccessDto.fromEntity(true);
        }
        Gifthub gifthub = new Gifthub(id);
        gifthubRepository.save(gifthub);

        return CommonSuccessDto.fromEntity(true);
    }

    private void validateItem(Long itemId) {
        if (!itemService.checkItem(itemId)) {
            throw new CommonException(ErrorCode.NOT_FOUND_ITEM);
        }
    }
}
