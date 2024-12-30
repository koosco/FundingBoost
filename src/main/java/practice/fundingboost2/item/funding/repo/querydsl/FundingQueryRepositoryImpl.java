package practice.fundingboost2.item.funding.repo.querydsl;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import practice.fundingboost2.item.funding.app.dto.GetFundingResponseDto;
import practice.fundingboost2.item.funding.repo.entity.QFunding;
import practice.fundingboost2.member.repo.entity.QRelation;

@Repository
@RequiredArgsConstructor
public class FundingQueryRepositoryImpl implements FundingQueryRepository {

    final JPAQueryFactory queryFactory;
    final QFunding funding = QFunding.funding;
    final QRelation relation = QRelation.relation;

    @Override
    public Page<GetFundingResponseDto> findFundings(Long memberId, Pageable pageable) {
        List<GetFundingResponseDto> dtos = queryFactory
            .selectFrom(funding)
            .where(funding.member.id.in(
                JPAExpressions.select(relation.id.member2Id)
                    .from(relation)
                    .where(relation.id.member1Id.eq(memberId))
            ))
            .orderBy(hasSort(pageable.getSort()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch()
            .stream()
            .map(GetFundingResponseDto::from)
            .toList();

        Long pageCount = queryFactory.select(funding.count())
            .from(funding)
            .where(funding.member.id.in(
                JPAExpressions.select(relation.id.member2Id)
                    .from(relation)
                    .where(relation.id.member1Id.eq(memberId))
            ))
            .fetchOne();

        pageCount = pageCount != null ? pageCount : 0;
        return new PageImpl<>(dtos, pageable, pageCount);
    }

    private OrderSpecifier<?> hasSort(Sort sort) {
        if (sort.isEmpty()) {
            return funding.createdAt.desc();
        }

        String order = sort.iterator().next().getProperty();

        return switch (order) {
            case "deadline" -> funding.deadLine.asc();
            default -> funding.createdAt.desc();
        };
    }
}
