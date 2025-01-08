package practice.fundingboost2.item.item.app.dto;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderItemRequestDtoTest {

    Validator validator;

    static ValidatorFactory factory;
    
    @BeforeEach
    void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    @AfterAll
    static void close() {
        factory.close();
    }
    
    @Test
    void givenItemIdNull_thenValidationFails() {
        // given
        OrderItemRequestDto orderItemRequestDto = new OrderItemRequestDto(null, 1L, 1);

        // when
        // then
        Set<ConstraintViolation<OrderItemRequestDto>> violations = validator.validate(orderItemRequestDto);
        assertThat(violations.size()).isEqualTo(1);
    }
    
    @Test
    void givenOptionIdNull_thenValidationFails() {
        // given
        OrderItemRequestDto orderItemRequestDto = new OrderItemRequestDto(1L, null, 1);

        // when
        // then
        Set<ConstraintViolation<OrderItemRequestDto>> violations = validator.validate(orderItemRequestDto);
        assertThat(violations.size()).isEqualTo(1);
    }


    @Test
    void givenQuantityZero_thenValidationFails() {
        // given
        OrderItemRequestDto orderItemRequestDto = new OrderItemRequestDto(1L, 1L, 0);

        // when
        // then
        Set<ConstraintViolation<OrderItemRequestDto>> violations = validator.validate(orderItemRequestDto);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    void givenQuantityOne_thenValidationPasses() {
        // given
        OrderItemRequestDto orderItemRequestDto = new OrderItemRequestDto(1L, 1L, 1);

        // when
        // then
        Set<ConstraintViolation<OrderItemRequestDto>> violations = validator.validate(orderItemRequestDto);
        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    void givenValidOrderItemRequestDto_thenValidationPasses() {
        // given
        OrderItemRequestDto orderItemRequestDto = new OrderItemRequestDto(1L, 1L, 1);

        // when
        // then
        Set<ConstraintViolation<OrderItemRequestDto>> violations = validator.validate(orderItemRequestDto);
        assertThat(violations.size()).isEqualTo(0);
    }
}