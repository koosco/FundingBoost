package practice.fundingboost2.item.order.repo.querydsl;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.repo.entity.Option;
import practice.fundingboost2.item.order.app.dto.GetOrderHistoryResponseDto;
import practice.fundingboost2.item.order.repo.entity.Delivery;
import practice.fundingboost2.item.order.repo.entity.Order;
import practice.fundingboost2.member.repo.entity.Member;

@SpringBootTest
@Transactional
class OrderQueryRepositoryImplTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private OrderQueryRepository orderQueryRepository;

    Member member;

    Delivery delivery;

    List<Item> items = new ArrayList<>();

    List<Order> orders = new ArrayList<>();

    final int ITEM_COUNT = 20;
    final int ORDER_COUNT = 20;

    @BeforeEach
    void init(){
        member = new Member("aa@aa.aa", "member", "imageUrl", "phoneNumber");
        em.persist(member);

        IntStream.range(0, ITEM_COUNT)
                .forEach(i -> {
                    Item item = new Item("item"+i, i*1000, "item_image"+i, "brand"+i, "categery"+i);
                    items.add(item);
                    em.persist(item);

                    Option option = new Option(item, item.getName()+"_option", 100);
                    em.persist(option);
                });

        delivery = new Delivery(member.getNickname(), "address", "phoneNumber", member);
        em.persist(delivery);

        IntStream.range(0, ORDER_COUNT)
                .forEach(i -> {
                    Order order = new Order(
                            member,
                            items.get(i),
                            items.get(i).getOptions().getFirst().getName(),
                            delivery);
                    orders.add(order);
                    em.persist(order);
                });
    }

    @Test
    public void givenTwentyOrders_whenGetMemberOrders_thenReturnTenDtos() {
        //given
        PageRequest pageRequest = PageRequest.of(0, 10);
        //when
        Page<GetOrderHistoryResponseDto> dtos = orderQueryRepository.getMemberOrders(member.getId(), pageRequest);
        //then
        assertThat(dtos.getContent()).hasSize(10);
    }

    @Test
    public void givenTwentyOrders_whenPageSizeFive_thenReturnFiveDtos() {
        //given
        PageRequest pageRequest = PageRequest.of(0, 5);
        //when
        Page<GetOrderHistoryResponseDto> dtos = orderQueryRepository.getMemberOrders(member.getId(), pageRequest);
        //then
        assertThat(dtos.getContent()).hasSize(5);
    }

    @Test
    public void givenTwentyOrders_whenGetMemberOrders_thenTotalElementSizeMustOrderSize() {
        //given
        PageRequest pageRequest = PageRequest.of(0, 10);
        //when
        Page<GetOrderHistoryResponseDto> dtos = orderQueryRepository.getMemberOrders(member.getId(), pageRequest);
        //then
        assertThat(dtos.getTotalElements()).isEqualTo(20);
    }

    @Test
    public void givenTwentyOrders_whenGetMemberOrders_thenTwoPageSize() {
        //given
        PageRequest pageRequest = PageRequest.of(0, 10);
        //when
        Page<GetOrderHistoryResponseDto> dtos = orderQueryRepository.getMemberOrders(member.getId(), pageRequest);
        //then
        assertThat(dtos.getTotalPages()).isEqualTo(2);
    }
}