package practice.fundingboost2.item.item.repo.querydsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import practice.fundingboost2.item.item.app.dto.GetMemberReviewResponseDto;
import practice.fundingboost2.item.item.app.dto.GetReviewResponseDto;

public interface ReviewQueryRepository {

    Page<GetReviewResponseDto> getReviews(Long itemId, Pageable pageable);

    Page<GetMemberReviewResponseDto> getMemberReviews(Long memberId, Pageable pageable);
}
