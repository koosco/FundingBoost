package practice.fundingboost2.item.item.app.dto;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreateReviewRequestDtoTest {

    Validator validator;
    
    @BeforeEach
    void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    @Test
    void givenNullItemId_thenThrowException() {
        // given
        // when
        CreateReviewRequestDto dto = new CreateReviewRequestDto(null, "content", 5);
        // then
        Set<ConstraintViolation<CreateReviewRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isNotEmpty();
    }
    
    @Test
    void givenNullScore_thenThrowException() {
        // given
        // when
        CreateReviewRequestDto dto = new CreateReviewRequestDto(1L, "content", null);
        // then
        Set<ConstraintViolation<CreateReviewRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void givenNotNullItemIdAndScore_thenShouldNotThrowException() {
        // given
        // when
        CreateReviewRequestDto dto = new CreateReviewRequestDto(1L, "content", 5);
        // then
        Set<ConstraintViolation<CreateReviewRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }
}