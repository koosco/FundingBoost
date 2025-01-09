package practice.fundingboost2.item.funding.repo.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.repo.entity.Option;
import practice.fundingboost2.member.repo.entity.Member;

@SpringBootTest
class FundingItemTest {

    Member member;
    Funding funding;

    final List<Item> items = new ArrayList<>();

    final int ITEM_SIZE = 6;

    @BeforeEach
    void init() {
        member = new Member("email", "nickname", "imageUrl", "phoneNumber");
        funding = new Funding(member, "message", "BIRTHDAY", LocalDateTime.now());

        for (int i = 1; i <= ITEM_SIZE; i++) {
            Item item = new Item("item" + i, i * 1000, "https://koosco.tistory.com", "brand" + i, "category" + i);
            items.add(item);
            new Option(item, "option1", 10);
        }
    }
    
    @Test
    void givenFunding_whenCreateFundingItem_thenFundingItemStatusMustBePENDING() {
        // given
        Item item = items.getFirst();
        Option option = item.getOptions().getFirst();

        // when
        FundingItem fundingItem = new FundingItem(funding, item, option, 1);

        // then
        assertThat(fundingItem.getStatus()).isEqualTo(FundingItemStatus.PENDING);
    }

    @Test
    void givenFunding_whenCreateFundingItem_thenFundingMustContainFundingItem() {
        // given
        Item item = items.getFirst();
        Option option = item.getOptions().getFirst();

        // when
        new FundingItem(funding, item, option, 1);

        // then
        assertThat(funding.getFundingItems()).hasSize(1);
    }

    @Test
    void givenFunding_whenCreateFiveFundingItem_thenFundingItemMustBeCreated() {
        // given
        // when
        for (int i = 0; i < 5; i++) {
            Item item = items.get(i);
            new FundingItem(funding, item, item.getOptions().getFirst(), i + 1);
        }
        // then
        assertThat(funding.getFundingItems()).hasSize(5);
    }

    @Test
    void givenFunding_whenSequenceIsFive_thenThrowException() {
        // given
        Item item = items.getFirst();
        Option option = item.getOptions().getFirst();

        // when
        // then
        assertThatThrownBy(() -> new FundingItem(funding, item, option, 6))
            .isInstanceOf(CommonException.class);
    }

    @Test
    void givenFunding_whenSequenceIsFive_thenFundingItemMustBeCreated() {
        // given
        Item item = items.getFirst();
        Option option = item.getOptions().getFirst();

        // when
        new FundingItem(funding, item, option, 5);

        // then
        assertThat(funding.getFundingItems()).hasSize(1);
    }
}