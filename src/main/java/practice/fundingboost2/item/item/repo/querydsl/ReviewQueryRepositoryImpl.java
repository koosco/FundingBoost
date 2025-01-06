package practice.fundingboost2.item.item.repo.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import practice.fundingboost2.item.item.app.dto.GetReviewResponseDto;
import practice.fundingboost2.item.item.repo.entity.QReview;
import practice.fundingboost2.member.repo.entity.QMember;

@Repository
@RequiredArgsConstructor
public class ReviewQueryRepositoryImpl implements ReviewQueryRepository {

    private final JPAQueryFactory queryFactory;
    private static final QReview review = QReview.review;
    private static final QMember member = QMember.member;

    @Override
    public Page<GetReviewResponseDto> getReviews(Long itemId, Pageable pageable) {
        List<GetReviewResponseDto> dtos = queryFactory
            .selectFrom(review)
            .where(review.item.id.eq(itemId))
            .leftJoin(review.member, member).fetchJoin()
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch()
            .stream()
            .map(GetReviewResponseDto::from)
            .toList();

        long pageCount = getPageCount(itemId);

        return new PageImpl<>(dtos, pageable, pageCount);
    }

    private long getPageCount(Long itemId) {
        Long count = queryFactory
            .select(review.count())
            .from(review)
            .where(review.item.id.eq(itemId))
            .fetchOne();

        return count != null ? count : 0;
    }
}
