package practice.fundingboost2.item.item.repo.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.lang.reflect.Field;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import practice.fundingboost2.common.exception.CommonException;

class ItemTest {

    Validator validator;

    Item item;
    
    Item priceNotNullItem;

    final int PRICE = 1000;

    @BeforeEach
    void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        item = new Item("name", null, "image", "brand", "ITEM_TEST");
        
        priceNotNullItem = new Item("name2", PRICE, "image2", "brand2", "ITEM_TEST2");
    }

    @Test
    public void givenPriceItem_whenCreated_thenPriceMustBeThousand() {
        //given
        //when
        //then
        assertThat(priceNotNullItem.getPrice()).isEqualTo(PRICE);
    }
    
    @Test
    void givenItem_whenCreated_thenCountMustBeZero() {
        // given
        // when
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
        item.mark(new BookmarkId(memberId, item.getId()));
        // then
        assertThat(item.getLikeCount()).isEqualTo(1);
    }

    @Test
    void givenMarkedItem_whenUnmarked_thenLikeCountMustDecrease() {
        // given
        Long memberId = 1L;
        item.mark(new BookmarkId(memberId, item.getId()));
        // when
        item.unmark();
        // then
        assertThat(item.getLikeCount()).isEqualTo(0);
    }

    @Test
    void givenItem_whenUnmarked_thenThrowException() {
        // given
        // when
        // then
        assertThatThrownBy(item::unmark)
            .isInstanceOf(CommonException.class);
    }

    @Test
    void givenItem_whenFunded_thenFundingCountMustIncrease() {
        // given
        // when
        item.fund();

        // then
        assertThat(item.getFundingCount()).isEqualTo(1);
    }

    @Test
    void givenItem_whenReview_thenReviewCountIncrease() {
        // given
        // when
        item.review();
        // then
        assertThat(item.getReviewCount()).isEqualTo(1);
    }
    
    @Test
    void givenItem_whenReviewCountIsNegative_thenValidationFails() throws NoSuchFieldException, IllegalAccessException {
        // given
        Field reviewCount = item.getClass().getDeclaredField("reviewCount");
        reviewCount.setAccessible(true);
        reviewCount.set(item, -1);
        // when
        // then
        Set<ConstraintViolation<Item>> violations = validator.validate(item);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void givenItem_whenFundingCountIsNegative_thenValidationFails() throws NoSuchFieldException, IllegalAccessException {
        // given
        Field fundingCount = item.getClass().getDeclaredField("fundingCount");
        fundingCount.setAccessible(true);
        fundingCount.set(item, -1);
        // when
        // then
        Set<ConstraintViolation<Item>> violations = validator.validate(item);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void givenItem_whenLikeCountIsNegative_thenValidationFails() throws NoSuchFieldException, IllegalAccessException {
        // given
        Field likeCount = item.getClass().getDeclaredField("likeCount");
        likeCount.setAccessible(true);
        likeCount.set(item, -1);
        // when
        // then
        Set<ConstraintViolation<Item>> violations = validator.validate(item);
        assertThat(violations).isNotEmpty();
    }
}