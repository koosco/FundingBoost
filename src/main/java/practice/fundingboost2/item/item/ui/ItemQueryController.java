package practice.fundingboost2.item.item.ui;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import practice.fundingboost2.common.dto.ResponseDto;
import practice.fundingboost2.config.security.annotation.Auth;
import practice.fundingboost2.item.item.repo.querydsl.ItemQueryRepository;
import practice.fundingboost2.item.item.ui.dto.GetItemDetailResponseDto;
import practice.fundingboost2.item.item.ui.dto.GetItemResponseDto;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemQueryController {

    private final ItemQueryRepository itemQueryRepository;

    @Operation(summary = "상품 목록 조회", description = "상품 목록을 조회합니다.")
    @SecurityRequirements
    @GetMapping
    public Page<GetItemResponseDto> getItems(
        @RequestParam(value = "category", required = false)
        String category,

        Pageable pageable) {
        return itemQueryRepository.getItems(category, pageable);
    }

    @Operation(summary = "좋아요 상품 조회", description = "회원이 좋아요한 상품을 조회합니다.")
    @GetMapping("/like")
    public ResponseDto<Page<GetItemResponseDto>> getLikedItems(
        @Auth
        Long memberId,

        Pageable pageable) {
        return ResponseDto.ok(itemQueryRepository.getLikedItems(memberId, pageable));
    }

    @Operation(summary = "상품 상세 조회", description = "상품 상세 정보를 조회합니다.")
    @SecurityRequirements
    @GetMapping("{item_id}")
    public ResponseDto<GetItemDetailResponseDto> getItemInfo(
        @Auth
        Long memberId,

        @PathVariable("item_id") Long itemId) {
        return ResponseDto.ok(itemQueryRepository.getItemInfo(memberId, itemId));
    }
}
