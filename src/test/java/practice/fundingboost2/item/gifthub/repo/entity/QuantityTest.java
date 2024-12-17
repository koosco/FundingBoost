package practice.fundingboost2.item.gifthub.repo.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import practice.fundingboost2.common.exception.CommonException;

@SpringBootTest
class QuantityTest {

    @Test
    void givenPositiveQuantity_whenCreate_thenCreateNewInstance() {
        // given
        Integer size = 1;
        // when
        Quantity quantity = new Quantity(size);
        // then
        assertThat(quantity.getQuantity()).isEqualTo(size);
    }

    @Test
    void givenNegativeQuantity_whenCreate_thenThrowException() {
        // given
        Integer size = -1;
        // when
        // then
        assertThrows(CommonException.class, () -> new Quantity(size));
    }

    @Test
    void givenZeroQuantity_whenCreate_thenThrowException() {
        // given
        Integer size = 0;
        // when
        // then
        assertThrows(CommonException.class, () -> new Quantity(size));
    }
}