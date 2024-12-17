package practice.fundingboost2.item.item.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.fundingboost2.config.security.annotation.Auth;
import practice.fundingboost2.item.item.app.dto.GetItemListResponseDto;
import practice.fundingboost2.item.item.repo.ItemQueryRepository;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemQueryController {

    private final ItemQueryRepository itemQueryRepository;

    @GetMapping
    public GetItemListResponseDto getItems(Pageable pageable) {
        return itemQueryRepository.getItems(pageable);
    }

    @GetMapping("/like")
    public GetItemListResponseDto getLikedItems(@Auth Long memberId, Pageable pageable) {
        return itemQueryRepository.getLikedItems(memberId, pageable);
    }
}
