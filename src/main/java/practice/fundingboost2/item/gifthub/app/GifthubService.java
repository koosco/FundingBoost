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
import practice.fundingboost2.item.item.repo.entity.Item;

@Service
@Transactional
@RequiredArgsConstructor
public class GifthubService {

    private final GifthubRepository gifthubRepository;
    private final ItemService itemService;

    public Gifthub findCart(GifthubId gifthubId) {
        return gifthubRepository.findById(gifthubId)
            .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_GIFTHUB_ITEM));
    }

    public CommonSuccessDto addToCart(Long memberId, Long itemId, Long optionId) {
        Item item = itemService.findItem(itemId);
        GifthubId id = new GifthubId(memberId, itemId, optionId);

        if (gifthubRepository.existsById(id)) {
            gifthubRepository.updateQuantityById(id);
            return CommonSuccessDto.fromEntity(true);
        }
        Gifthub gifthub = new Gifthub(id, item);
        gifthubRepository.save(gifthub);

        return CommonSuccessDto.fromEntity(true);
    }

    public CommonSuccessDto deleteFromCart(Long memberId, Long itemId, Long optionId) {
        Gifthub cart = findCart(new GifthubId(memberId, itemId, optionId));
        gifthubRepository.delete(cart);

        return CommonSuccessDto.fromEntity(true);
    }

    public CommonSuccessDto updateCart(Long memberId, Long itemId, Long optionId, Integer quantity) {
        Gifthub cart = findCart(new GifthubId(memberId, itemId, optionId));

        cart.updateQuantity(quantity);

        return CommonSuccessDto.fromEntity(true);
    }
}
