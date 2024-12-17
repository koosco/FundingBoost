package practice.fundingboost2.item.gifthub.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.common.dto.ResponseDto;
import practice.fundingboost2.config.security.annotation.Auth;
import practice.fundingboost2.item.gifthub.app.GifthubService;

@RestController
@RequestMapping("/api/gifthub")
@RequiredArgsConstructor
public class GifthubController {

    private final GifthubService gifthubService;

    @PostMapping
    public ResponseDto<CommonSuccessDto> addItemToCart(@Auth Long memberId, @RequestParam Long itemId,
        @RequestBody Long optionId) {
        return ResponseDto.ok(gifthubService.addToCart(memberId, itemId, optionId));
    }

    @DeleteMapping
    public ResponseDto<CommonSuccessDto> deleteGifthub(@Auth Long memberId, @RequestParam Long itemId,
        @RequestBody Long optionId) {
        return ResponseDto.ok(gifthubService.deleteGifthub(memberId, itemId, optionId));
    }
//
//    @PatchMapping("/{item_id}")
//    public ResponseDto<GifthubResponseDto> updateGifthub(@Auth Long memberId, @PathVariable("item_id") Long itemId,
//        @RequestBody
//        UpdateGifthubRequestDto dto) {
//        return ResponseDto.ok(gifthubService.updateGifthub(memberId, itemId, dto));
//    }
}
