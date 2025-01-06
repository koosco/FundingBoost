package practice.fundingboost2.item.item.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.fundingboost2.common.dto.ResponseDto;
import practice.fundingboost2.item.item.app.dto.GetReviewResponseDto;
import practice.fundingboost2.item.item.repo.querydsl.ReviewQueryRepository;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ReviewQueryController {

    private final ReviewQueryRepository reviewQueryRepository;

    @GetMapping("/{item_id}")
    public ResponseDto<Page<GetReviewResponseDto>> getReviews(@PathVariable("item_id") Long itemId, Pageable pageable) {
        return ResponseDto.ok(reviewQueryRepository.getReviews(itemId, pageable));
    }
}
