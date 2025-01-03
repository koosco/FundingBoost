package practice.fundingboost2.item.gifthub.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.item.gifthub.repo.entity.Gifthub;
import practice.fundingboost2.item.gifthub.repo.entity.GifthubId;
import practice.fundingboost2.item.item.repo.ItemRepository;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.repo.entity.Option;
import practice.fundingboost2.member.repo.MemberRepository;
import practice.fundingboost2.member.repo.entity.Member;

@SpringBootTest
public class GifthubServiceUpdateTest {

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
        for (String optionName : optionNames) {
            Option option = new Option(item, optionName, optionPrice);
        }
        item = itemRepository.save(item);
        option = item.getOptions().getFirst();

        member = new Member("member", "name", "imageUrl", "phone");
        member = memberRepository.save(member);
    }

    @Test
    void givenGifthub_whenUpdateQuantityToThree_thenQuantityUpdate() {
        // given
        gifthubService.addToCart(member.getId(), item.getId(), option.getId());

        // when
        CommonSuccessDto dto = gifthubService.updateCart(member.getId(), item.getId(), option.getId(), 3);
        Gifthub findGifthub = gifthubService.findGifthub(new GifthubId(member.getId(), item.getId(), option.getId()));

        // then
        assertThat(dto.isSuccess()).isTrue();
        assertThat(findGifthub.getQuantity()).isEqualTo(3);
    }

    @Test
    void givenGifthub_whenUpdateWithQuantityZERO_thenThrowException() {
        // given
        gifthubService.addToCart(member.getId(), item.getId(), option.getId());
        // when
        // then
        assertThatThrownBy(() -> gifthubService.updateCart(member.getId(), item.getId(), option.getId(), 0))
            .isInstanceOf(CommonException.class);
    }

    @Test
    void givenGifthub_whenUpdateWithQuantityNegative_thenThrowException() {
        // given
        gifthubService.addToCart(member.getId(), item.getId(), option.getId());
        // when
        // then
        assertThatThrownBy(() -> gifthubService.updateCart(member.getId(), item.getId(), option.getId(), -1))
            .isInstanceOf(CommonException.class);
    }
}
