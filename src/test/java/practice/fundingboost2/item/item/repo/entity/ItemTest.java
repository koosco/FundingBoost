package practice.fundingboost2.item.item.repo.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import practice.fundingboost2.common.exception.CommonException;

class ItemTest {

    @Test
    void givenItem_whenCreated_thenCountMustBeZero() {
        // given
        // when
        Item item = new Item("name", null, "image", "brand", "ITEM_TEST");
        // then
        assertThat(item.getLikeCount()).isEqualTo(0);
        assertThat(item.getFundingCount()).isEqualTo(0);
        assertThat(item.getReviewCount()).isEqualTo(0);
    }

    @Test
    void givenItem_whenMarked_thenLikeCountMustIncrease() {
        // given
        Long memberId = 1L;
        // when
        Item item = new Item("name", null, "image", "brand", "ITEM_TEST");
        item.mark(new BookmarkId(memberId, item.getId()));
        // then
        assertThat(item.getLikeCount()).isEqualTo(1);
    }

    @Test
    void givenMarkedItem_whenUnmarked_thenLikeCountMustDecrease() {
        // given
        Long memberId = 1L;
        Item item = new Item("name", null, "image", "brand", "ITEM_TEST");
        item.mark(new BookmarkId(memberId, item.getId()));
        // when
        item.unmark();
        // then
        assertThat(item.getLikeCount()).isEqualTo(0);
    }

    @Test
    void givenItem_whenUnmarked_thenThrowException() {
        // given
        Item item = new Item("name", null, "image", "brand", "ITEM_TEST");

        // when
        // then
        assertThatThrownBy(item::unmark)
            .isInstanceOf(CommonException.class);
    }

    @Test
    void givenItem_whenFunded_thenFundingCountMustIncrease() {
        // given
        Item item = new Item("name", null, "image", "brand", "ITEM_TEST");

        // when
        item.fund();

        // then
        assertThat(item.getFundingCount()).isEqualTo(1);
    }
}