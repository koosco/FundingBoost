package practice.fundingboost2.auth.app.dto;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoginRequestDtoTest {

    static ValidatorFactory factory;

    Validator validator;

    @BeforeEach
    void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void afterAll() {
        if (factory != null) {
            factory.close();
        }
    }

    @Test
    void whenEmailBlank_thenValidationFails() {
        // given
        // when
        LoginRequestDto dto = new LoginRequestDto("", "password");
        // then
        Set<ConstraintViolation<LoginRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void whenEmailNull_thenValidationFails() {
        // given
        // when
        LoginRequestDto dto = new LoginRequestDto(null, "password");
        // then
        Set<ConstraintViolation<LoginRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void givenPasswordNull_thenValidationFails() {
        // given
        // when
        LoginRequestDto dto = new LoginRequestDto("email@email.com", null);
        // then
        Set<ConstraintViolation<LoginRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void givenPasswordBlank_thenValidationFails() {
        // given
        // when
        LoginRequestDto dto = new LoginRequestDto("email@email.com", "");
        // then
        Set<ConstraintViolation<LoginRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void givenEmailAndPassword_thenValidationPasses() {
        // given
        // when
        LoginRequestDto dto = new LoginRequestDto("email@email.com", "password");
        // then
        Set<ConstraintViolation<LoginRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }

    @Test
    void givenSevenLengthPassword_thenValidationFails() {
        // given
        // when
        LoginRequestDto dto = new LoginRequestDto("email@email.com", "1234567");
        // then
        Set<ConstraintViolation<LoginRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void givenEightLengthPassword_thenValidationPasses() {
        // given
        // when
        LoginRequestDto dto = new LoginRequestDto("email@email.com", "12345678");
        // then
        Set<ConstraintViolation<LoginRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }

    @Test
    void givenNotFormatedEmail_thenValidationFails() {
        // given
        // when
        LoginRequestDto dto = new LoginRequestDto("email", "password");
        // then
        Set<ConstraintViolation<LoginRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isNotEmpty();
    }
}