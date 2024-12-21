package practice.fundingboost2.item.funding.repo.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        assertDoesNotThrow(() -> funding.validateMember(member));
    }
    
    @Test
    void givenFunding_whenValidateOther_thenThrowException() {
        // given
        Member other = new Member("email", "other", "imageUrl", "phoneNumber");

        // when
        // then
        assertThrows(CommonException.class, () -> funding.validateMember(other));
    }
    
    @Test
    void givenOutdatedFunding_whenUpdate_thenThrowException() {
        // given
        // when
        // then
        assertThrows(CommonException.class, () -> funding.update(LocalDateTime.now(), "PENDING"));
    }
    
    @Test
    void givenFailedFunding_whenUpdate_thenThrowException() {
        // given
        funding.update(null, "FAILED");

        // when
        // then
        assertThrows(CommonException.class, () -> funding.update(LocalDateTime.now(), "SUCCESS"));
    }

    @Test
    void givenSuccessFunding_whenUpdate_thenThrowException() {
        // given
        funding.update(null, "SUCCESS");

        // when
        // then
        assertThrows(CommonException.class, () -> funding.update(LocalDateTime.now(), "PENDING"));
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
}