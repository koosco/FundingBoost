package practice.fundingboost2.item.order.app;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.item.item.app.dto.OrderItemRequestDto;
import practice.fundingboost2.item.item.repo.ItemRepository;
import practice.fundingboost2.item.item.repo.OptionRepository;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.repo.entity.Option;
import practice.fundingboost2.item.order.app.dto.CreateOrderRequestDto;
import practice.fundingboost2.item.order.repo.DeliveryRepository;
import practice.fundingboost2.item.order.repo.entity.Delivery;
import practice.fundingboost2.member.repo.MemberRepository;
import practice.fundingboost2.member.repo.entity.Member;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    Member member;

    List<Item> items = new ArrayList<>();

    Delivery delivery;

    final int ITEM_COUNT = 5;
    final int OPTION_LIST_SIZE = 5;

    @BeforeEach
    void init(){
        member = new Member("email@email", "member", "imageUrl", "phoneNumber");
        member.increasePoint(20000);
        memberRepository.save(member);

        for(int i = 1; i <= ITEM_COUNT; i++){
            Item item = new Item("item"+i, 1000 * i, "imageUrl"+i, "brand"+i, "category"+i);
            items.add(item);
            itemRepository.save(item);

            for (int j = 1; j <= OPTION_LIST_SIZE; j++) {
                Option option = new Option(item, item.getName() +" option"+j, 100);
                optionRepository.save(option);
            }
        }


        delivery = new Delivery("friend", "address", "phoneNumber", member);
        deliveryRepository.save(delivery);
    }

    @Test
    public void givenRequestDto_whenCreateOrder_thenReturnDto() {
        //given
        List<OrderItemRequestDto> orderItemRequestDto = IntStream.range(0, 3)
                .mapToObj(i -> new OrderItemRequestDto(
                        items.get(i).getId(),
                        items.get(i).getOptions().get(i).getId(),
                        i+1
                ))
                .toList();
        CreateOrderRequestDto createOrderRequestDto = new CreateOrderRequestDto(delivery.getId(), orderItemRequestDto);
        //when
        CommonSuccessDto commonSuccessDto = orderService.createOrder(member.getId(), createOrderRequestDto);
        //then
        Assertions.assertTrue(commonSuccessDto.isSuccess());
    }

    @Test
    public void givenNotInvalidQuantity_whenValidateQuantity_thenThrowCommonException() throws Exception {
        //given
        List<OrderItemRequestDto> orderItemRequestDto = IntStream.range(0, 3)
                .mapToObj(i -> new OrderItemRequestDto(
                        items.get(i).getId(),
                        items.get(i).getOptions().get(i).getId(),
                        i
                ))
                .toList();
        CreateOrderRequestDto createOrderRequestDto = new CreateOrderRequestDto(delivery.getId(), orderItemRequestDto);
        //when
        //then
        assertThatThrownBy(() -> orderService.createOrder(member.getId(), createOrderRequestDto))
                .isInstanceOf(CommonException.class);
    }
}