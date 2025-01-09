package practice.fundingboost2.item.funding.repo.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.member.repo.entity.Member;

@SpringBootTest
class FundingTest {

    Member member;

    Funding funding;

    @BeforeEach
    void init() {
        member = new Member("email", "nickname", "imageUrl", "phoneNumber");
        funding = new Funding(member, "message", "BIRTHDAY", LocalDateTime.now().plusDays(1));
    }

    @Test
    void givenFunding_whenCollectPriceEqualsToTotalPrice_thenMustThrowException() {
        // given
        final int totalPrice = 1000;
        final int extraPrice = 1000;
        funding.plusTotalPrice(totalPrice);
        funding.fund(totalPrice);

        // when
        // then
        assertThatThrownBy(() -> funding.fund(extraPrice))
            .isInstanceOf(CommonException.class);
    }

    @Test
    void givenFunding_whenCollectPriceLessThanTotalPrice_thenFundMustSuccess() {
        // given
        final int totalPrice = 1000;
        final int fundPrice = 500;
        funding.plusTotalPrice(totalPrice);
        // when
        funding.fund(fundPrice);

        // then
        assertThat(funding.getCollectPrice()).isEqualTo(fundPrice);
    }

    @Test
    void givenOutdatedFunding_whenFund_thenThrowException() {
        // given
        Funding funding = new Funding(member, "message", "BIRTHDAY", LocalDateTime.now().minusDays(1));
        // when
        // then
        assertThatThrownBy(() -> funding.fund(1000))
            .isInstanceOf(CommonException.class);
    }

    @Test
    void givenTerminatedFunding_whenFund_thenThrowException() {
        // given
        Funding funding = new Funding(member, "message", "BIRTHDAY", LocalDateTime.now().plusDays(1));
        funding.update(null, "SUCCESS");
        // when
        // then
        assertThatThrownBy(() -> funding.fund(1000))
            .isInstanceOf(CommonException.class);
    }

    @Test
    void givenMember_whenCreateFunding_thenFundingPriceMustBeZero() {
        // given
        // when
        // then
        assertThat(funding.getFundingItems()).isEmpty();
        assertThat(funding.getTotalPrice()).isEqualTo(0);
        assertThat(funding.getCollectPrice()).isEqualTo(0);
        assertThat(funding.getStatus()).isEqualTo(FundingStatus.PENDING);
        assertThat(funding.getTag()).isEqualTo(FundingTag.BIRTHDAY);
        assertThat(funding.getMessage()).isEqualTo("message");
    }

    @Test
    void givenFunding_whenValidateMy_thenMustNotThrowException() {
        // given
        // when
        // then
        assertThatNoException()
            .isThrownBy(() -> funding.validateMember(member));
    }

    @Test
    void givenFunding_whenValidateOther_thenThrowException() {
        // given
        Member other = new Member("email", "other", "imageUrl", "phoneNumber");

        // when
        // then
        assertThatThrownBy(() -> funding.validateMember(other))
            .isInstanceOf(CommonException.class);
    }

    @Test
    void givenOutdatedFunding_whenUpdate_thenThrowException() {
        // given
        Funding funding = new Funding(member, "message", "BIRTHDAY", LocalDateTime.now().minusDays(1));
        // when
        // then
        assertThatThrownBy(() -> funding.update(LocalDateTime.now(), "PENDING"))
            .isInstanceOf(CommonException.class);
    }

    @Test
    void givenFailedFunding_whenUpdate_thenThrowException() {
        // given
        funding.update(null, "FAILED");

        // when
        // then
        assertThatThrownBy(() -> funding.update(LocalDateTime.now(), "PENDING"))
            .isInstanceOf(CommonException.class);
    }

    @Test
    void givenSuccessFunding_whenUpdate_thenThrowException() {
        // given
        funding.update(null, "SUCCESS");

        // when
        // then
        assertThatThrownBy(() -> funding.update(LocalDateTime.now(), "PENDING"))
            .isInstanceOf(CommonException.class);
    }

    @Test
    void givenFunding_whenUpdateDeadline_thenDeadlineMustBeUpdated() {
        // given
        LocalDateTime newDeadline = LocalDateTime.now().plusDays(2);

        // when
        funding.update(newDeadline, null);

        // then
        assertThat(funding.getDeadLine()).isEqualTo(newDeadline);
    }

    @Test
    void givenFundingPrice_whenNotExceedFundingPrice_thenFundingSuccess() {
        // given
        funding.plusTotalPrice(1000);
        funding.fund(900);

        // when
        funding.fund(200);

        // then
        assertThat(funding.getCollectPrice()).isEqualTo(1100);
    }
    
    @Test
    void givenTotalPriceAndCollectPriceThousand_whenFund_thenThrowException() {
        // given
        funding.plusTotalPrice(1000);
        funding.fund(1000);

        // when
        // then
        assertThatThrownBy(() -> funding.fund(1000))
            .isInstanceOf(CommonException.class);
    }

    @Test
    void givenExceedCollectPriceExceedTotalPrice_whenFund_thenThrowException() {
        // given
        funding.plusTotalPrice(1000);
        funding.fund(10000);

        // when
        // then
        assertThatThrownBy(() -> funding.fund(1000))
            .isInstanceOf(CommonException.class);
    }
}