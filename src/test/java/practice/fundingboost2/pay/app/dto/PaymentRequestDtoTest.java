package practice.fundingboost2.pay.app.dto;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentRequestDtoTest {

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
    void givenMerchantUidEmpty_thenValidationFails() {
        // given
        PaymentRequestDto dto = new PaymentRequestDto("", "impUid", "buyerName", "buyerEmail", "01012345678", "address",
            "postcode", 1000, "productName");

        // when
        // then
        Set<ConstraintViolation<PaymentRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenMerchantUidBlank_thenValidationFails() {
        // given
        PaymentRequestDto dto = new PaymentRequestDto(" ", "impUid", "buyerName", "buyerEmail", "01012345678",
            "address", "postcode", 1000, "productName");

        // when
        // then
        Set<ConstraintViolation<PaymentRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenMerchantUidNull_thenValidationFails() {
        // given
        PaymentRequestDto dto = new PaymentRequestDto(null, "impUid", "buyerName", "buyerEmail", "01012345678",
            "address", "postcode", 1000, "productName");

        // when
        // then
        Set<ConstraintViolation<PaymentRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenImpUidEmpty_thenValidationFails() {
        // given
        PaymentRequestDto dto = new PaymentRequestDto("merchantUid", "", "buyerName", "buyerEmail", "01012345678",
            "address", "postcode", 1000, "productName");

        // when
        // then
        Set<ConstraintViolation<PaymentRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenImpUidBlank_thenValidationFails() {
        // given
        PaymentRequestDto dto = new PaymentRequestDto("merchantUid", " ", "buyerName", "buyerEmail", "01012345678",
            "address", "postcode", 1000, "productName");

        // when
        // then
        Set<ConstraintViolation<PaymentRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenImpUidNull_thenValidationFails() {
        // given
        PaymentRequestDto dto = new PaymentRequestDto("merchantUid", null, "buyerName", "buyerEmail", "01012345678",
            "address", "postcode", 1000, "productName");

        // when
        // then
        Set<ConstraintViolation<PaymentRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenBuyerNameEmpty_thenValidationFails() {
        // given
        PaymentRequestDto dto = new PaymentRequestDto("merchantUid", "impUid", "", "buyerEmail", "01012345678",
            "address", "postcode", 1000, "productName");

        // when
        // then
        Set<ConstraintViolation<PaymentRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenBuyerNameBlank_thenValidationFails() {
        // given
        PaymentRequestDto dto = new PaymentRequestDto("merchantUid", "impUid", " ", "buyerEmail", "01012345678",
            "address", "postcode", 1000, "productName");

        // when
        // then
        Set<ConstraintViolation<PaymentRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenBuyerNameNull_thenValidationFails() {
        // given
        PaymentRequestDto dto = new PaymentRequestDto("merchantUid", "impUid", null, "buyerEmail", "01012345678",
            "address", "postcode", 1000, "productName");

        // when
        // then
        Set<ConstraintViolation<PaymentRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenBuyerEmailEmpty_thenValidationFails() {
        // given
        PaymentRequestDto dto = new PaymentRequestDto("merchantUid", "impUid", "buyerName", "", "01012345678",
            "address", "postcode", 1000, "productName");

        // when
        // then
        Set<ConstraintViolation<PaymentRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenBuyerEmailBlank_thenValidationFails() {
        // given
        PaymentRequestDto dto = new PaymentRequestDto("merchantUid", "impUid", "buyerName", " ", "01012345678",
            "address", "postcode", 1000, "productName");

        // when
        // then
        Set<ConstraintViolation<PaymentRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenBuyerEmailNull_thenValidationFails() {
        // given
        PaymentRequestDto dto = new PaymentRequestDto("merchantUid", "impUid", "buyerName", null, "01012345678",
            "address", "postcode", 1000, "productName");

        // when
        // then
        Set<ConstraintViolation<PaymentRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenPhoneNumberEmpty_thenValidationFails() {
        // given
        PaymentRequestDto dto = new PaymentRequestDto("merchantUid", "impUid", "buyerName", "buyerEmail", "",
            "address", "postcode", 1000, "productName");

        // when
        // then
        Set<ConstraintViolation<PaymentRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenPhoneNumberBlank_thenValidationFails() {
        // given
        PaymentRequestDto dto = new PaymentRequestDto("merchantUid", "impUid", "buyerName", "buyerEmail", " ",
            "address", "postcode", 1000, "productName");

        // when
        // then
        Set<ConstraintViolation<PaymentRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenPhoneNumberNull_thenValidationFails() {
        // given
        PaymentRequestDto dto = new PaymentRequestDto("merchantUid", "impUid", "buyerName", "buyerEmail", null,
            "address", "postcode", 1000, "productName");

        // when
        // then
        Set<ConstraintViolation<PaymentRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenAddressEmpty_thenValidationFails() {
        // given
        PaymentRequestDto dto = new PaymentRequestDto("merchantUid", "impUid", "buyerName", "buyerEmail", "01012345678",
            "", "postcode", 1000, "productName");

        // when
        // then
        Set<ConstraintViolation<PaymentRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenAddressBlank_thenValidationFails() {
        // given
        PaymentRequestDto dto = new PaymentRequestDto("merchantUid", "impUid", "buyerName", "buyerEmail", "01012345678",
            " ", "postcode", 1000, "productName");

        // when
        // then
        Set<ConstraintViolation<PaymentRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenAddressNull_thenValidationFails() {
        // given
        PaymentRequestDto dto = new PaymentRequestDto("merchantUid", "impUid", "buyerName", "buyerEmail", "01012345678",
            null, "postcode", 1000, "productName");

        // when
        // then
        Set<ConstraintViolation<PaymentRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenPostcodeEmpty_thenValidationFails() {
        // given
        PaymentRequestDto dto = new PaymentRequestDto("merchantUid", "impUid", "buyerName", "buyerEmail", "01012345678",
            "address", "", 1000, "productName");

        // when
        // then
        Set<ConstraintViolation<PaymentRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenPostcodeBlank_thenValidationFails() {
        // given
        PaymentRequestDto dto = new PaymentRequestDto("merchantUid", "impUid", "buyerName", "buyerEmail", "01012345678",
            "address", " ", 1000, "productName");

        // when
        // then
        Set<ConstraintViolation<PaymentRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenPostcodeNull_thenValidationFails() {
        // given
        PaymentRequestDto dto = new PaymentRequestDto("merchantUid", "impUid", "buyerName", "buyerEmail", "01012345678",
            "address", null, 1000, "productName");

        // when
        // then
        Set<ConstraintViolation<PaymentRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenAmountNull_thenValidationFails() {
        // given
        PaymentRequestDto dto = new PaymentRequestDto("merchantUid", "impUid", "buyerName", "buyerEmail", "01012345678",
            "address", "postcode", null, "productName");

        // when
        // then
        Set<ConstraintViolation<PaymentRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenProductNameEmpty_thenValidationFails() {
        // given
        PaymentRequestDto dto = new PaymentRequestDto("merchantUid", "impUid", "buyerName", "buyerEmail", "01012345678",
            "address", "postcode", 1000, "");

        // when
        // then
        Set<ConstraintViolation<PaymentRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenProductNameBlank_thenValidationFails() {
        // given
        PaymentRequestDto dto = new PaymentRequestDto("merchantUid", "impUid", "buyerName", "buyerEmail", "01012345678",
            "address", "postcode", 1000, " ");

        // when
        // then
        Set<ConstraintViolation<PaymentRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void givenProductNameNull_thenValidationFails() {
        // given
        PaymentRequestDto dto = new PaymentRequestDto("merchantUid", "impUid", "buyerName", "buyerEmail", "01012345678",
            "address", "postcode", 1000, null);

        // when
        // then
        Set<ConstraintViolation<PaymentRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }
}