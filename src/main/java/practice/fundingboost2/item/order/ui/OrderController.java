package practice.fundingboost2.item.order.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.common.dto.ResponseDto;
import practice.fundingboost2.config.security.annotation.Auth;
import practice.fundingboost2.item.order.app.OrderService;
import practice.fundingboost2.item.order.app.dto.CreateOrderRequestDto;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/item")
    public ResponseDto<CommonSuccessDto> createOrderFromItems(@Auth Long memberId, @RequestBody CreateOrderRequestDto dto) {
        return ResponseDto.created(orderService.createOrder(memberId, dto));
    }

    @PostMapping("/gifthub")
    public ResponseDto<CommonSuccessDto> createOrderFromGifthub(@Auth Long memberId, @RequestBody CreateOrderRequestDto dto) {
        return ResponseDto.created(orderService.createOrderFromGifthub(memberId, dto));
    }
}

