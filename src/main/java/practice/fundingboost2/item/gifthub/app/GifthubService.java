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
import practice.fundingboost2.member.app.MemberService;
import practice.fundingboost2.member.repo.entity.Member;

@Service
@Transactional
@RequiredArgsConstructor
public class GifthubService {

    private final GifthubRepository gifthubRepository;
    private final MemberService memberService;
    private final ItemService itemService;

    public Gifthub findCart(GifthubId gifthubId) {
        return gifthubRepository.findById(gifthubId)
            .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_GIFTHUB_ITEM));
    }

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

    public CommonSuccessDto deleteGifthub(Long memberId, Long itemId) {
        Gifthub cart = findCart(new GifthubId(memberId, itemId));
        gifthubRepository.delete(cart);

        return CommonSuccessDto.fromEntity(true);
    }
}
