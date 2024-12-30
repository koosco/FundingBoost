package practice.fundingboost2.item.funding.repo.jpa;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import practice.fundingboost2.item.funding.repo.entity.Funding;
import practice.fundingboost2.item.funding.repo.entity.QFunding;
import practice.fundingboost2.item.funding.repo.entity.QFundingItem;
import practice.fundingboost2.item.funding.app.dto.GetFundingListResponseDto;
import practice.fundingboost2.member.repo.entity.QMember;
import practice.fundingboost2.member.repo.entity.QRelation;

@Repository
@RequiredArgsConstructor
public class FundingQueryRepositoryImpl implements FundingQueryRepository {

    final JPAQueryFactory queryFactory;
    final QFunding funding = QFunding.funding;
    final QMember member = QMember.member;
    final QRelation relation = QRelation.relation;
    final QFundingItem fundingItem = QFundingItem.fundingItem;

    @Override
    public GetFundingListResponseDto findFundings(Long memberId) {
        List<Funding> fundings = queryFactory
            .selectFrom(funding)
            .join(funding.member, member).fetchJoin()
            .leftJoin(funding.fundingItems, fundingItem).fetchJoin()
            .where(funding.member.id.in(findFriendIds(memberId)))
            .fetch();

        return GetFundingListResponseDto.from(fundings);
    }

    private JPQLQuery<Long> findFriendIds(Long memberId) {
        return JPAExpressions.select(relation.id.member2Id)
            .from(relation)
            .where(relation.id.member1Id.eq(memberId));
    }
}
