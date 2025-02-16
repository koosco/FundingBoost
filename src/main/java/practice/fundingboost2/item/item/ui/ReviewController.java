package practice.fundingboost2.item.item.ui;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.common.dto.ResponseDto;
import practice.fundingboost2.config.security.annotation.Auth;
import practice.fundingboost2.item.item.app.ReviewService;
import practice.fundingboost2.item.item.app.dto.CreateReviewRequestDto;
import practice.fundingboost2.item.item.app.dto.GetReviewResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item/review")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 생성", description = "리뷰를 생성합니다.")
    @PostMapping
    public ResponseDto<GetReviewResponseDto> createReview(
        @Auth
        Long memberId,

        @Validated
        @RequestBody
        CreateReviewRequestDto dto,

        List<MultipartFile> files) {
        return ResponseDto.created(reviewService.createReview(memberId, dto, files));
    }

    @Operation(summary = "리뷰 삭제", description = "리뷰를 삭제합니다.")
    @DeleteMapping("/{review_id}")
    public ResponseDto<CommonSuccessDto> deleteReview(
        @Auth
        Long memberId,

        @PathVariable("review_id")
        Long reviewId) {
        return ResponseDto.ok(reviewService.deleteReview(memberId, reviewId));
    }
}
