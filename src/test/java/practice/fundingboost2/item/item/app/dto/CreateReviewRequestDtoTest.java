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

class CreateReviewRequestDtoTest {

    Validator validator;

    static ValidatorFactory factory;
    
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
    void givenNullOrderId_thenThrowException() {
        // given
        // when
        CreateReviewRequestDto dto = new CreateReviewRequestDto(1L, null, "content", 5);
        // then
        Set<ConstraintViolation<CreateReviewRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isNotEmpty();
    }
    
    @Test
    void givenNullItemId_thenThrowException() {
        // given
        // when
        CreateReviewRequestDto dto = new CreateReviewRequestDto(null, 1L, "content", 5);
        // then
        Set<ConstraintViolation<CreateReviewRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isNotEmpty();
    }
    
    @Test
    void givenNullScore_thenThrowException() {
        // given
        // when
        CreateReviewRequestDto dto = new CreateReviewRequestDto(1L, 1L, "content", null);
        // then
        Set<ConstraintViolation<CreateReviewRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void givenNotNullItemIdAndScore_thenShouldNotThrowException() {
        // given
        // when
        CreateReviewRequestDto dto = new CreateReviewRequestDto(1L, 1L, "content", 5);
        // then
        Set<ConstraintViolation<CreateReviewRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }
}