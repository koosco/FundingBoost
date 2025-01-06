package practice.fundingboost2.item.item.repo.entity;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import practice.fundingboost2.member.repo.entity.Member;

class OptionTest {

    Member member;

    Item item;

    @BeforeEach
    void init() {
        member = new Member("email", "nickname", "imageUrl", "phoneNumber");
        item = new Item("name", 1000, "image", "brand", "OPTION_TEST");
    }

    @Test
    void givenItem_whenOptionCreated_thenItemMustContainCreatedOption() {
        // given
        // when
        Option option = new Option(item, "option", 100);
        // then
        assertThat(item.getOptions()).hasSize(1);
        assertThat(item.getOptions().getFirst()).isEqualTo(option);
    }

    @Test
    void givenQuantity_whenOptionCreated_thenQuantityMustEqual() {
        // given
        final int QUANTITY_SIZE = 100;
        // when
        Option option = new Option(item, "option", QUANTITY_SIZE);
        // then
        assertThat(option.getQuantity()).isEqualTo(QUANTITY_SIZE);
    }
}