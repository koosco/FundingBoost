package practice.fundingboost2.item.order.app;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.common.exception.ErrorCode;
import practice.fundingboost2.item.item.app.ItemService;
import practice.fundingboost2.item.item.app.dto.OrderItemRequestDto;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.repo.entity.Option;
import practice.fundingboost2.item.order.app.dto.CreateOrderRequestDto;
import practice.fundingboost2.item.order.repo.OrderRepository;
import practice.fundingboost2.item.order.repo.entity.Delivery;
import practice.fundingboost2.item.order.repo.entity.Order;
import practice.fundingboost2.member.app.MemberService;
import practice.fundingboost2.member.repo.entity.Member;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final MemberService memberService;
    private final ItemService itemService;
    private final DeliveryService deliveryService;
    private final OrderRepository orderRepository;

    public Order findOrder(Long orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ORDER));
    }

    @Transactional
    public CommonSuccessDto createOrder(Long memberId, CreateOrderRequestDto dto) {
        Member member = memberService.findMember(memberId);
        Delivery delivery = deliveryService.findDelivery(dto.deliveryId());

        List<Order> orders = new ArrayList<>();

        dto.orderItemListRequestDto().forEach(itemInfo -> {
            Order order = createOrderEntity(member, delivery, itemInfo);
            int price = itemService.findItem(itemInfo.itemId()).getPrice();
            int quantity = itemInfo.quantity();
            validateQuantity(quantity);
            member.decreasePoint(price*quantity);
            orders.add(order);
        });
        orderRepository.saveAll(orders);

        return CommonSuccessDto.fromEntity(true);
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }
    }

    private Order createOrderEntity(Member member, Delivery delivery, OrderItemRequestDto itemInfo) {
        Item item = itemService.findItem(itemInfo.itemId());
        Option option = findOption(item, itemInfo.optionId(), itemInfo.quantity());
        return new Order(member, item, option.getName(), delivery);
    }

    private Option findOption(Item item, Long optionId, int quantity) {
        for(Option option : item.getOptions()){
            if(option.getId().equals(optionId)){
                option.minusQuantity(quantity);
                return option;
            }
        }
        throw new CommonException(ErrorCode.NOT_FOUND_OPTION);
    }
}
