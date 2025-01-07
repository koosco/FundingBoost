package practice.fundingboost2.item.order.app;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.item.item.app.ItemService;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.order.app.dto.CreateOrderRequestDto;
import practice.fundingboost2.item.order.repo.OrderRepository;
import practice.fundingboost2.item.order.repo.entity.Delivery;
import practice.fundingboost2.item.order.repo.entity.Order;
import practice.fundingboost2.member.app.MemberService;
import practice.fundingboost2.member.repo.entity.Member;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final MemberService memberService;
    private final ItemService itemService;
    private final DeliveryService deliveryService;
    private final OrderRepository orderRepository;

    public CommonSuccessDto createOrder(Long memberId, CreateOrderRequestDto dto) {
        Member member = memberService.findMember(memberId);
        List<Item> items = itemService.findItemIn(dto.itemIds().ids());
        Delivery delivery = deliveryService.findDelivery(dto.deliveryId());

        List<Order> orders = items.stream()
            .map(item -> new Order(member, item, delivery))
            .toList();
        orderRepository.saveAll(orders);

        return CommonSuccessDto.fromEntity(true);
    }

    public boolean existsByMemberId(Long memberId) {
        return orderRepository.existsByMember_Id(memberId);
    }
}
