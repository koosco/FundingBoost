package practice.fundingboost2.item.order.repo.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import practice.fundingboost2.item.order.app.dto.GetOrderHistoryResponseDto;
import practice.fundingboost2.item.order.repo.entity.QOrder;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepositoryImpl implements OrderQueryRepository{

    private final JPAQueryFactory queryFactory;

    private static final QOrder order = QOrder.order;

    @Override
    public Page<GetOrderHistoryResponseDto> getMemberOrders(Long memberId, Pageable pageable) {
        List<GetOrderHistoryResponseDto> dtos = queryFactory
                .selectFrom(order)
                .where(order.member.id.eq(memberId))
                .orderBy(order.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(GetOrderHistoryResponseDto::from)
                .toList();

        long pageCount = getPageCount(memberId);

        return new PageImpl<>(dtos, pageable, pageCount);
    }

    private long getPageCount(Long memberId) {
        Long count = queryFactory
                .select(order.count())
                .from(order)
                .where(order.member.id.eq(memberId))
                .fetchOne();

        return count != null ? count : 0;
    }
}
