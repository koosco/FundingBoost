package practice.fundingboost2.item.gifthub.app;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.item.gifthub.repo.entity.GifthubId;
import practice.fundingboost2.item.item.repo.ItemRepository;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.repo.entity.Option;
import practice.fundingboost2.member.repo.MemberRepository;
import practice.fundingboost2.member.repo.entity.Member;

@SpringBootTest
public class GifthubServiceDeleteTest {

    @Autowired
    GifthubService gifthubService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    Item item;

    Member member;

    Option option;

    @BeforeEach
    void init() {
        item = new Item("item", 1000, "url", "brand", "category");

        List<String> optionNames = List.of("option1", "option2", "option3");
        int optionPrice = 100;
        optionNames.forEach(optionName -> new Option(item, optionName, optionPrice));
        item = itemRepository.save(item);
        option = item.getOptions().getFirst();

        member = new Member("member", "name", "imageUrl", "phone");
        member = memberRepository.save(member);
    }

    @Test
    void givenGifthub_whenDeleteFromCart_thenMustSuccess() {
        // given
        gifthubService.addToCart(member.getId(), item.getId(), option.getId());
        // when
        gifthubService.deleteFromCart(member.getId(), item.getId(), option.getId());
        // then
        assertThatThrownBy(() -> gifthubService.findGifthub(new GifthubId(member.getId(), item.getId(), option.getId())))
            .isInstanceOf(CommonException.class);
    }

    @Test
    void givenNotExistsItem_whenDeleteFromCart_thenThrowException() {
        // given
        Long notExistsItemId = 100000000000000L;

        // when
        // then
        assertThatThrownBy(() -> gifthubService.deleteFromCart(member.getId(), notExistsItemId, 1L))
            .isInstanceOf(CommonException.class);
    }
}
