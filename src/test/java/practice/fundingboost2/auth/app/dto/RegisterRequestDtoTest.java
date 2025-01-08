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

class RegisterRequestDtoTest {

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
        RegisterRequestDto dto = new RegisterRequestDto("", "password", "nickname");
        // then
        Set<ConstraintViolation<RegisterRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void whenEmailNull_thenValidationFails() {
        // given
        // when
        RegisterRequestDto dto = new RegisterRequestDto(null, "password", "nickname");
        // then
        Set<ConstraintViolation<RegisterRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void givenPasswordNull_thenValidationFails() {
        // given
        // when
        RegisterRequestDto dto = new RegisterRequestDto("email@email.com", null, "nickname");
        // then
        Set<ConstraintViolation<RegisterRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void givenPasswordBlank_thenValidationFails() {
        // given
        // when
        RegisterRequestDto dto = new RegisterRequestDto("email@email.com", "", "nickname");
        // then
        Set<ConstraintViolation<RegisterRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void givenEmailAndPassword_thenValidationPasses() {
        // given
        // when
        RegisterRequestDto dto = new RegisterRequestDto("email@email.com", "password", "nickname");
        // then
        Set<ConstraintViolation<RegisterRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }

    @Test
    void givenSevenLengthPassword_thenValidationFails() {
        // given
        // when
        RegisterRequestDto dto = new RegisterRequestDto("email@email.com", "1234567", "nickname");
        // then
        Set<ConstraintViolation<RegisterRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void givenEightLengthPassword_thenValidationPasses() {
        // given
        // when
        RegisterRequestDto dto = new RegisterRequestDto("email@email.com", "12345678", "nickname");
        // then
        Set<ConstraintViolation<RegisterRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }

    @Test
    void givenNotFormatedEmail_thenValidationFails() {
        // given
        // when
        RegisterRequestDto dto = new RegisterRequestDto("email", "password", "nickname");
        // then
        Set<ConstraintViolation<RegisterRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isNotEmpty();
    }
    
    @Test
    void givenBlankNickname_thenValidationFails() {
        // given
        // when
        RegisterRequestDto dto = new RegisterRequestDto("email", "password", "");
        // then
        Set<ConstraintViolation<RegisterRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void givenNullNickname_thenValidationFails() {
        // given
        // when
        RegisterRequestDto dto = new RegisterRequestDto("email", "password", null);
        // then
        Set<ConstraintViolation<RegisterRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isNotEmpty();
    }
}