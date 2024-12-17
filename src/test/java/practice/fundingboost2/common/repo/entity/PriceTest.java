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
}