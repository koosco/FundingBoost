package practice.fundingboost2.item.order.ui;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.fundingboost2.common.dto.ResponseDto;
import practice.fundingboost2.config.security.annotation.Auth;
import practice.fundingboost2.item.order.app.dto.GetOrderHistoryResponseDto;
import practice.fundingboost2.item.order.repo.querydsl.OrderQueryRepository;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderQueryController {

    private final OrderQueryRepository orderQueryRepository;

    @Operation(summary = "주문 내역 조회", description = "회원의 주문 내역을 조회합니다.")
    @GetMapping("")
    public ResponseDto<Page<GetOrderHistoryResponseDto>> getMemberOrders(@Auth Long memberId, Pageable pageable) {
        return ResponseDto.ok(orderQueryRepository.getMemberOrders(memberId, pageable));
    }

}
