package practice.fundingboost2.item.order.app.dto;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeliveryRequestDtoTest {

    Validator validator;

    static ValidatorFactory factory;
    
    @BeforeEach
    void init() {
        factory = jakarta.validation.Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    @AfterAll
    static void close() {
        factory.close();
    }
    
    @Test
    void givenUserNameEmpty_thenValidationFails() {
        // given
        DeliveryRequestDto dto = new DeliveryRequestDto("", "address", "01012345678");

        // when
        // then
        Set<ConstraintViolation<DeliveryRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenUserNameBlank_thenValidationFails() {
        // given
        DeliveryRequestDto dto = new DeliveryRequestDto(" ", "address", "01012345678");

        // when
        // then
        Set<ConstraintViolation<DeliveryRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenUserNameNull_thenValidationFails() {
        // given
        DeliveryRequestDto dto = new DeliveryRequestDto(null, "address", "01012345678");

        // when
        // then
        Set<ConstraintViolation<DeliveryRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenAddressEmpty_thenValidationFails() {
        // given
        DeliveryRequestDto dto = new DeliveryRequestDto("userName", "", "01012345678");

        // when
        // then
        Set<ConstraintViolation<DeliveryRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenAddressBlank_thenValidationFails() {
        // given
        DeliveryRequestDto dto = new DeliveryRequestDto("userName", " ", "01012345678");

        // when
        // then
        Set<ConstraintViolation<DeliveryRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenAddressNull_thenValidationFails() {
        // given
        DeliveryRequestDto dto = new DeliveryRequestDto("userName", null, "01012345678");

        // when
        // then
        Set<ConstraintViolation<DeliveryRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenPhoneNumberEmpty_thenValidationFails() {
        // given
        DeliveryRequestDto dto = new DeliveryRequestDto("userName", "address", "");

        // when
        // then
        Set<ConstraintViolation<DeliveryRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenPhoneNumberBlank_thenValidationFails() {
        // given
        DeliveryRequestDto dto = new DeliveryRequestDto("userName", "address", " ");

        // when
        // then
        Set<ConstraintViolation<DeliveryRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenPhoneNumberNull_thenValidationFails() {
        // given
        DeliveryRequestDto dto = new DeliveryRequestDto("userName", "address", null);

        // when
        // then
        Set<ConstraintViolation<DeliveryRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenPhoneNumberLengthEleven_thenValidationPasses() {
        // given
        DeliveryRequestDto dto = new DeliveryRequestDto("userName", "address", "01012345678");

        // when
        // then
        Set<ConstraintViolation<DeliveryRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }

    @Test
    void givenPhoneNumberLengthTwelve_thenValidationFails() {
        // given
        DeliveryRequestDto dto = new DeliveryRequestDto("userName", "address", "010123456789");

        // when
        // then
        Set<ConstraintViolation<DeliveryRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }
}