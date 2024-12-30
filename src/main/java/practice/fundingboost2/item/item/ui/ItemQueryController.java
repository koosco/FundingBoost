package practice.fundingboost2.item.item.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import practice.fundingboost2.config.security.annotation.Auth;
import practice.fundingboost2.item.item.ui.dto.GetItemDetailResponseDto;
import practice.fundingboost2.item.item.ui.dto.GetItemListResponseDto;
import practice.fundingboost2.item.item.repo.querydsl.ItemQueryRepository;
import practice.fundingboost2.item.item.ui.dto.GetItemResponseDto;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemQueryController {

    private final ItemQueryRepository itemQueryRepository;

    @GetMapping
    public Page<GetItemResponseDto> getItems(@RequestParam(value = "category", required = false) String category, Pageable pageable) {
        return itemQueryRepository.getItems(category, pageable);
    }

    @GetMapping("/like")
    public GetItemListResponseDto getLikedItems(@Auth Long memberId, Pageable pageable) {
        return itemQueryRepository.getLikedItems(memberId, pageable);
    }

    @GetMapping("{item_id}")
    public GetItemDetailResponseDto getItemInfo(@Auth Long memberId, @PathVariable("item_id") Long itemId) {
        return itemQueryRepository.getItemInfo(memberId, itemId);
    }
}
