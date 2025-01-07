package practice.fundingboost2.item.order.repo.querydsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import practice.fundingboost2.item.order.app.dto.GetOrderHistoryResponseDto;

public interface OrderQueryRepository {

    Page<GetOrderHistoryResponseDto> getMemberOrders(Long memberId, Pageable pageable);
}
