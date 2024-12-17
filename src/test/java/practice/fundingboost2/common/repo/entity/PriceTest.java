package practice.fundingboost2.common.repo.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import practice.fundingboost2.common.exception.CommonException;

@SpringBootTest
class PriceTest {

    @Test
    void givenZero_whenCreate_thenCreatePrice() {
        // given
        // when
        Price price = new Price(0);
        // then
        assertThat(price.getPrice()).isEqualTo(0);
    }

    @Test
    void givenNegative_whenCreate_thenThrowException() {
        // given
        // when
        // then
        assertThrows(CommonException.class, () -> new Price(-1));
    }

    @Test
    void givenPositive_whenCreate_thenCreatePrice() {
        // given
        // when
        Price price = new Price(1);
        // then
        assertThat(price.getPrice()).isEqualTo(1);
    }

    @Test
    void givenMaximumInteger_whenCreate_thenCreatePrice() {
        // given
        // when
        Price price = new Price(Integer.MAX_VALUE);
        // then
        assertThat(price.getPrice()).isEqualTo(Integer.MAX_VALUE);
    }

    @Test
    void givenMaximumIntegerPlusOne_whenCreate_thenThrowException() {
        // given
        // when
        // then
        assertThrows(CommonException.class, () -> new Price(Integer.MAX_VALUE + 1));
    }
    
    @Test
    void givenTwoPrice_whenAdd_thenReturnAddedPrice() {
        // given
        Price price1 = new Price(10_000);
        Price price2 = new Price(20_000);
        // when
        Price newPrice = price1.add(price2);
        // then
        assertThat(newPrice.getPrice()).isEqualTo(30_000);
    }

    @Test
    void givenTwoPrice_whenPriceEquals_thenInstancesAreEqual() {
        // given
        Price price1 = new Price(10_000);
        Price price2 = new Price(10_000);

        // when
        // then
        assertThat(price1).isEqualTo(price2);
    }

    @Test
    void givenTwoPrice_whenPriceDifferent_thenInstancesAreDifferent() {
        // given
        Price price1 = new Price(10_000);
        Price price2 = new Price(20_000);

        // when
        // then
        assertThat(price1).isNotEqualTo(price2);
    }
}