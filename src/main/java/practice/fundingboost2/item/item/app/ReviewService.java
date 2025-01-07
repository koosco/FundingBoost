package practice.fundingboost2.item.item.app;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.common.exception.ErrorCode;
import practice.fundingboost2.common.file.FileService;
import practice.fundingboost2.item.item.app.dto.CreateReviewRequestDto;
import practice.fundingboost2.item.item.app.dto.GetReviewResponseDto;
import practice.fundingboost2.item.item.repo.ReviewRepository;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.repo.entity.Review;
import practice.fundingboost2.item.order.app.OrderService;
import practice.fundingboost2.member.app.MemberService;
import practice.fundingboost2.member.repo.entity.Member;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final MemberService memberService;
    private final OrderService orderService;
    private final ItemService itemService;
    private final ReviewRepository reviewRepository;
    private final FileService fileService;

    @Transactional
    public GetReviewResponseDto createReview(Long memberId, CreateReviewRequestDto dto, List<MultipartFile> files) {
        Member member = memberService.findMember(memberId);
        validateOrder(memberId);
        Item item = itemService.findItem(dto.itemId());

        List<String> reviewImages = fileService.uploadMultipleFiles(files);
        Review review = new Review(dto.score(), dto.content(), member, item, reviewImages);
        review = reviewRepository.save(review);

        return new GetReviewResponseDto(memberId, review.getId(),
            member.getNickname(), member.getImageUrl(), review.getScore(),
            review.getCreatedAt(), review.getContent(), review.getReviewImages());
    }

    private void validateOrder(Long memberId) {
        if (!orderService.existsByMemberId(memberId)) {
            throw new CommonException(ErrorCode.NOT_FOUND_PURCHASED_ORDER);
        }
    }
}
