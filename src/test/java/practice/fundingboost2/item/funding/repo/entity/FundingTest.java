package practice.fundingboost2.item.funding.repo.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import practice.fundingboost2.member.repo.entity.Member;

@SpringBootTest
class FundingTest {

    @Test
    void givenMember_whenCreateFunding_thenFundingPriceMustBeZero() {
        // given
        Member member = new Member("email", "nickname", "imageUrl", "phoneNumber");

        // when
        Funding funding = new Funding(member, "message", "BIRTHDAY", LocalDateTime.now());

        // then
        assertThat(funding.getFundingItems()).isEmpty();
        assertThat(funding.getTotalPrice()).isEqualTo(0);
        assertThat(funding.getCollectPrice()).isEqualTo(0);
        assertThat(funding.getStatus()).isEqualTo(FundingStatus.PENDING);
        assertThat(funding.getTag()).isEqualTo(FundingTag.BIRTHDAY);
        assertThat(funding.getMessage()).isEqualTo("message");
    }
}