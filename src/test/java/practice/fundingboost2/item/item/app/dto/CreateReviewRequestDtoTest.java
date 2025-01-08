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
    void givenNullOrderId_thenValidationFails() {
        // given
        // when
        CreateReviewRequestDto dto = new CreateReviewRequestDto(1L, null, "content", 5);
        // then
        Set<ConstraintViolation<CreateReviewRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }
    
    @Test
    void givenNullItemId_thenValidationFails() {
        // given
        // when
        CreateReviewRequestDto dto = new CreateReviewRequestDto(null, 1L, "content", 5);
        // then
        Set<ConstraintViolation<CreateReviewRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }
    
    @Test
    void givenNullScore_thenValidationFails() {
        // given
        // when
        CreateReviewRequestDto dto = new CreateReviewRequestDto(1L, 1L, "content", null);
        // then
        Set<ConstraintViolation<CreateReviewRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenNotNullItemIdAndScore_thenValidationPasses() {
        // given
        // when
        CreateReviewRequestDto dto = new CreateReviewRequestDto(1L, 1L, "content", 5);
        // then
        Set<ConstraintViolation<CreateReviewRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }
    
    @Test
    void givenScoreZero_thenValidationFails() {
        // given
        // when
        CreateReviewRequestDto dto = new CreateReviewRequestDto(1L, 1L, "content", 0);
        // then
        Set<ConstraintViolation<CreateReviewRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }
    
    @Test
    void givenScoreOne_thenValidationPasses() {
        // given
        // when
        CreateReviewRequestDto dto = new CreateReviewRequestDto(1L, 1L, "content", 1);
        // then
        Set<ConstraintViolation<CreateReviewRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }
    
    @Test
    void givenScoreFive_thenValidationPasses() {
        // given
        // when
        CreateReviewRequestDto dto = new CreateReviewRequestDto(1L, 1L, "content", 5);
        // then
        Set<ConstraintViolation<CreateReviewRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }

    @Test
    void givenScoreSix_thenValidationFails() {
        // given
        // when
        CreateReviewRequestDto dto = new CreateReviewRequestDto(1L, 1L, "content", 6);
        // then
        Set<ConstraintViolation<CreateReviewRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }
}