package practice.fundingboost2.item.item.repo.entity;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import practice.fundingboost2.member.repo.entity.Member;

class ReviewTest {

    Member member;

    Item item;

    Validator validator;

    @BeforeEach
    void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        member = new Member("email", "nickname", "imageUrl", "phoneNumber");
        item = new Item("name", null, "image", "brand", "ITEM_TEST");
    }

    @Test
    void whenCreateReview_thenItemReviewCountMustIncrease() {
        // given
        // when
        Review review = new Review(5, "content", member, item, "optionName");

        // then
        assertThat(item.getReviewCount()).isEqualTo(1);
    }

    @Test
    void givenReviewImages_whenCreateReview_thenReviewImagesMustContains() {
        // given
        List<String> images = List.of("image1", "image2", "image3");

        // when
        Review review = new Review(5, "content", member, item, "optionName", images);
        // then
        assertThat(review.getReviewImages()).hasSize(images.size());
    }

    @Test
    void givenReview_whenScoreIsZero_thenValidationFails() {
        // given
        // when
        Review review = new Review(0, "content", member, item, "optionName");
        // then
        Set<ConstraintViolation<Review>> violations = validator.validate(review);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void givenReview_whenScoreIsOne_thenValidationPasses() {
        // given
        // when
        Review review = new Review(1, "content", member, item, "optionName");
        // then
        Set<ConstraintViolation<Review>> violations = validator.validate(review);
        assertThat(violations).isEmpty();
    }

    @Test
    void givenReview_whenScoreIsFive_thenValidationPasses() {
        // given
        // when
        Review review = new Review(5, "content", member, item, "optionName");
        // then
        Set<ConstraintViolation<Review>> violations = validator.validate(review);
        assertThat(violations).isEmpty();
    }

    @Test
    void givenReview_whenScoreIsSix_thenValidationFails() {
        // given
        // when
        Review review = new Review(6, "content", member, item, "optionName");
        // then
        Set<ConstraintViolation<Review>> violations = validator.validate(review);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void givenReview_whenImageSizeIsThree_thenValidationPasses() {
        // given
        List<String> images = List.of("image1", "image2", "image3");
        // when
        Review review = new Review(5, "content", member, item, "optionName", images);
        // then
        Set<ConstraintViolation<Review>> violations = validator.validate(review);
        assertThat(violations).isEmpty();
    }

    @Test
    void givenReview_whenImageSizeIsFour_thenValidationFails() {
        // given
        List<String> images = List.of("image1", "image2", "image3", "image4");
        // when
        Review review = new Review(5, "content", member, item, "optionName", images);
        // then
        Set<ConstraintViolation<Review>> violations = validator.validate(review);
        assertThat(violations).isNotEmpty();
    }
}