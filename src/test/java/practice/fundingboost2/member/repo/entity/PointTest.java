package practice.fundingboost2.member.repo.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import practice.fundingboost2.common.exception.CommonException;

@SpringBootTest
class PointTest {

    @Test
    void givenNegativePoint_whenCreatePoint_thenThrowException() {
        // given
        // when
        // then
        assertThrows(CommonException.class, () -> new Point(-1));
    }

    @Test
    void givenPositivePoint_whenCreatePoint_thenDoesNotThrowException() {
        // given
        // when
        // then
        assertDoesNotThrow(() -> new Point(1));
    }

    @Test
    void givenZeroPoint_whenCreatePoint_thenDoesNotThrowException() {
        // given
        // when
        // then
        assertDoesNotThrow(() -> new Point(0));
    }

    @Test
    void givenCreatePoint_whenMinusToNegative_thenThrowException() {
        // given
        Point point = new Point(1);
        // when
        // then
        assertThrows(CommonException.class, () -> point.subtract(new Point(2)));
    }

    @Test
    void givenCreatePoint_whenMinusToPositive_thenDoesNotThrowException() {
        // given
        Point point = new Point(2);
        // when
        // then
        assertDoesNotThrow(() -> point.subtract(new Point(1)));
    }

    @Test
    void givenCreatePoint_whenMinusToZero_thenDoesNotThrowException() {
        // given
        Point point = new Point(1);
        // when
        // then
        assertDoesNotThrow(() -> point.subtract(new Point(1)));
    }
}