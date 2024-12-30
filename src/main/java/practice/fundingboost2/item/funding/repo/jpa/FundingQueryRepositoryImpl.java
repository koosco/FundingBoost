package practice.fundingboost2.item.funding.repo.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import practice.fundingboost2.item.funding.app.dto.GetFundingListResponseDto;
import practice.fundingboost2.item.funding.repo.entity.Funding;
import practice.fundingboost2.item.funding.repo.entity.QFunding;
import practice.fundingboost2.member.repo.entity.QRelation;

@Repository
@RequiredArgsConstructor
public class FundingQueryRepositoryImpl implements FundingQueryRepository {

    final JPAQueryFactory queryFactory;
    final QFunding funding = QFunding.funding;
    final QRelation relation = QRelation.relation;

    @Override
    public GetFundingListResponseDto findFundings(Long memberId, Pageable pageable) {
        List<Long> ids = findFriendIds(memberId, pageable);
        List<Funding> fundings = queryFactory
            .selectFrom(funding)
            .where(funding.member.id.in(ids))
            .fetch();

        return GetFundingListResponseDto.from(fundings);
    }

    private List<Long> findFriendIds(Long memberId, Pageable pageable) {
        return queryFactory.select(relation.id.member2Id)
            .from(relation)
            .where(relation.id.member1Id.eq(memberId))
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();
    }
}
