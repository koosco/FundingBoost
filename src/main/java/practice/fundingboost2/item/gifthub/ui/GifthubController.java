package practice.fundingboost2.item.gifthub.ui;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @Operation(summary = "장바구니 추가", description = "장바구니에 상품을 추가합니다.")
    @PostMapping("/{item_id}/{option_id}")
    public ResponseDto<CommonSuccessDto> addItemToCart(
        @Auth
        Long memberId,

        @PathVariable("item_id")
        Long itemId,

        @PathVariable("option_id")
        Long optionId) {
        return ResponseDto.ok(gifthubService.addToCart(memberId, itemId, optionId));
    }

    @Operation(summary = "장바구니 삭제", description = "장바구니에서 상품을 삭제합니다.")
    @DeleteMapping("{item_id}/{option_id}")
    public ResponseDto<CommonSuccessDto> deleteItemFromCart(
        @Auth
        Long memberId,

        @PathVariable("item_id")
        Long itemId,

        @PathVariable("option_id")
        Long optionId) {
        return ResponseDto.ok(gifthubService.deleteFromCart(memberId, itemId, optionId));
    }

    @Operation(summary = "장바구니 수정", description = "장바구니에 담긴 상품을 수정합니다.")
    @PatchMapping("/{item_id}/{option_id}")
    public ResponseDto<CommonSuccessDto> updateItemFromCart(
        @Auth
        Long memberId,

        @PathVariable("item_id")
        Long itemId,

        @PathVariable("option_id")
        Long optionId,

        @RequestParam("quantity")
        Integer quantity) {
        return ResponseDto.ok(gifthubService.updateCart(memberId, itemId, optionId, quantity));
    }
}
