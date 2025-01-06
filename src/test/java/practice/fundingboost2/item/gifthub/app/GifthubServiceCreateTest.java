package practice.fundingboost2.item.gifthub.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

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
class GifthubServiceCreateTest {

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
    void whenCreateGifthub_thenQuantityMustBeOne() {
        // given
        // when
        CommonSuccessDto dto = gifthubService.addToCart(member.getId(), item.getId(), option.getId());
        Gifthub findGifthub = gifthubService.findGifthub(
            new GifthubId(member.getId(), item.getId(), item.getOptions().getFirst().getId()));

        // then
        assertTrue(dto.isSuccess());
        assertThat(findGifthub.getQuantity()).isEqualTo(1);
        assertThat(findGifthub.getId().getMemberId()).isEqualTo(member.getId());
        assertThat(findGifthub.getId().getItemId()).isEqualTo(item.getId());
    }

    @Test
    void givenOneGifthub_whenCreateGifthub_thenQuantityMustBeTwo() {
        // given
        gifthubService.addToCart(member.getId(), item.getId(), option.getId());

        // when
        CommonSuccessDto dto = gifthubService.addToCart(member.getId(), item.getId(), option.getId());
        Gifthub findGifthub = gifthubService.findGifthub(new GifthubId(member.getId(), item.getId(), option.getId()));

        // then
        assertTrue(dto.isSuccess());
        assertThat(findGifthub.getQuantity()).isEqualTo(2);
        assertThat(findGifthub.getId().getMemberId()).isEqualTo(member.getId());
        assertThat(findGifthub.getId().getItemId()).isEqualTo(item.getId());
    }

    @Test
    void givenMyGifthub_whenOtherCreateGifthub_thenQuantityMustBeOne() {
        // given
        Member other = new Member("member2", "name", "imageUrl", "phone");
        other = memberRepository.save(other);

        gifthubService.addToCart(member.getId(), item.getId(), option.getId());

        // when
        CommonSuccessDto dto = gifthubService.addToCart(other.getId(), item.getId(), option.getId());
        Gifthub findGifthub = gifthubService.findGifthub(new GifthubId(member.getId(), item.getId(), option.getId()));

        // then
        assertThat(dto.isSuccess()).isTrue();
        assertThat(findGifthub.getQuantity()).isEqualTo(1);
    }

    @Test
    void GivenItem_whenCreateWithNotExistsItem_thenThrowException() {
        // given
        Long notExistsItemId = 100000000000000L;
        // when
        // then
        assertThatThrownBy(() -> gifthubService.addToCart(member.getId(), notExistsItemId, 1L))
            .isInstanceOf(CommonException.class);
    }
}