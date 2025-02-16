package practice.fundingboost2.item.item.ui;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.common.dto.ResponseDto;
import practice.fundingboost2.config.security.annotation.Auth;
import practice.fundingboost2.item.item.app.ItemService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
public class ItemController {

    private final ItemService itemService;

    @Operation(summary = "상품 좋아요", description = "상품을 좋아요합니다. ")
    @PutMapping("/like/{item_id}")
    public ResponseDto<CommonSuccessDto> likeItem(
        @Auth
        Long memberId,

        @PathVariable("item_id")
        Long itemId) {
        return ResponseDto.ok(itemService.likeItem(memberId, itemId));
    }
}
