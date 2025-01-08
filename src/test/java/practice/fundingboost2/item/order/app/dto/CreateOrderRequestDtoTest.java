package practice.fundingboost2.item.order.app.dto;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import practice.fundingboost2.item.item.app.dto.OrderItemRequestDto;

class CreateOrderRequestDtoTest {

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
    void whenDeliveryIdNull_thenValidationFails() {
        // given
        List<OrderItemRequestDto> orderItemListRequestDto = List.of(new OrderItemRequestDto(1L, 1L, 1));
        CreateOrderRequestDto dto = new CreateOrderRequestDto(null, orderItemListRequestDto);

        // when
        // then
        assertThat(validator.validate(dto)).hasSize(1);
    }

    @Test
    void whenOrderItemRequestEmpty_thenValidationFails() {
        // given
        CreateOrderRequestDto dto = new CreateOrderRequestDto(1L, List.of());

        // when
        // then
        assertThat(validator.validate(dto)).hasSize(1);
    }
    
    @Test
    void whenDeliveryIdNullAndOrderItemDtoEmpty_thenValidationFails() {
        // given
        CreateOrderRequestDto dto = new CreateOrderRequestDto(null, List.of());

        // when
        // then
        assertThat(validator.validate(dto)).hasSize(2);
    }

    @Test
    void whenDeliveryIdNotNullAndDtoNotEmpty_thenValidationPasses() {
        // given
        List<OrderItemRequestDto> orderItemListRequestDto = List.of(new OrderItemRequestDto(1L, 1L, 1));
        CreateOrderRequestDto dto = new CreateOrderRequestDto(1L, orderItemListRequestDto);

        // when
        // then
        assertThat(validator.validate(dto)).isEmpty();
    }
}