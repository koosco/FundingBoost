package practice.fundingboost2.pay.app;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MerchantUidGeneratorTest {

    MerchantUidGenerator generator;

    @BeforeEach
    void init() {
        generator = new MerchantUidGenerator();
    }

    @Test
    void whenGenerate_thenGeneratedValuePrefixMustEqualsToPrefix() {
        // given
        // when
        String generatedUid = generator.generate();
        // then
        assertThat(generatedUid).startsWith("FB-");
    }

    @Test
    void whenGenerate_thenGeneratedValueLengthMustBeFourteen() {
        // given
        // when
        String generatedUid = generator.generate();
        // then
        assertThat(generatedUid).hasSize(14);
    }
    
    @Test
    void whenGenerate_thenGeneratedValueDateMustBeFormat_yyMMdd() {
        // given
        // when
        String generatedUid = generator.generate();
        // then
        assertThat(generatedUid.substring(3, 9)).matches("\\d{6}");
    }
    
    @Test
    void whenGenerate_thenGeneratedValueRandomValueMustBeInRange() {
        // given
        // when
        String generatedUid = generator.generate();
        // then
        assertThat(generatedUid.substring(9)).matches("[0-9a-z]{5}");
    }
}