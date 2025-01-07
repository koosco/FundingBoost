package practice.fundingboost2.item.order.app;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.item.gifthub.repo.entity.Gifthub;
import practice.fundingboost2.item.gifthub.repo.entity.GifthubId;
import practice.fundingboost2.item.item.app.dto.OrderItemRequestDto;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.repo.entity.Option;
import practice.fundingboost2.item.order.app.dto.CreateOrderRequestDto;
import practice.fundingboost2.item.order.repo.entity.Delivery;
import practice.fundingboost2.member.repo.entity.Member;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @PersistenceContext
    EntityManager em;

    Member member;

    List<Item> items = new ArrayList<>();

    List<Gifthub> gifthubs = new ArrayList<>();

    Delivery delivery;

    final int ITEM_COUNT = 5;
    final int OPTION_LIST_SIZE = 5;

    @BeforeEach
    void init(){
        member = new Member("email@email", "member", "imageUrl", "phoneNumber");
        em.persist(member);

        for(int i = 0; i < ITEM_COUNT; i++){
            Item item = new Item("item"+i, 1000 * i, "imageUrl"+i, "brand"+i, "category"+i);
            items.add(item);
            em.persist(item);

            for (int j = 0; j < OPTION_LIST_SIZE; j++) {
                Option option = new Option(item, item.getName() +" option"+j, 100);
                em.persist(option);
            }
        }

        IntStream.range(0, ITEM_COUNT)
                .forEach(i -> {
                    GifthubId gifthubId = new GifthubId(member.getId(), items.get(i).getId(), items.get(i).getOptions().get(i).getId());
                    Gifthub gifthub = new Gifthub(gifthubId);
                    gifthub.updateQuantity(i+1);
                    gifthubs.add(gifthub);
                    em.persist(gifthub);
                });


        delivery = new Delivery("friend", "address", "phoneNumber", member);
        em.persist(delivery);

    }

    @Test
    public void givenRequestDto_whenCreateOrder_thenReturnDto() {
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
        CommonSuccessDto commonSuccessDto = orderService.createOrder(member.getId(), createOrderRequestDto);
        //then
        Assertions.assertTrue(commonSuccessDto.isSuccess());
    }

    @Test
    public void givenGifthubAndRequestDto_whenCreateOrderFromGifthub_thenReturnDto() throws Exception {
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
        CommonSuccessDto commonSuccessDto = orderService.createOrderFromGifthub(member.getId(), createOrderRequestDto);
        //then
        Assertions.assertTrue(commonSuccessDto.isSuccess());
    }
}