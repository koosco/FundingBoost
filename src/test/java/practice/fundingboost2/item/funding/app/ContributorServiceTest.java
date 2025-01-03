package practice.fundingboost2.item.funding.app;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import practice.fundingboost2.item.funding.app.interfaces.FundingRepository;
import practice.fundingboost2.item.funding.repo.entity.Contributor;
import practice.fundingboost2.item.funding.repo.entity.Funding;
import practice.fundingboost2.item.funding.repo.jpa.ContributorRepository;
import practice.fundingboost2.member.repo.MemberRepository;
import practice.fundingboost2.member.repo.entity.Member;

@SpringBootTest
class ContributorServiceTest {

    @Autowired
    ContributorService contributorService;

    @Autowired
    ContributorRepository contributorRepository;

    @Autowired
    FundingRepository fundingRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void givenContributor_whenSave_thenCanFindContributor() {
        // given
        Member member = new Member("email", "name", "image", "phone");
        member.increasePoint(100_000);
        Member friend = new Member("email", "name", "image", "phone");
        friend.increasePoint(100_000);
        Funding funding = new Funding(member, "message", "BIRTHDAY", LocalDateTime.now().plusDays(10));
        funding.plusTotalPrice(10_000);
        Contributor contributor = new Contributor(friend, funding, 1000);
        memberRepository.save(member);
        memberRepository.save(friend);
        fundingRepository.save(funding);

        // when
        Contributor savedContributor = contributorService.save(contributor);

        // then
        Optional<Contributor> findContributor = contributorRepository.findById(savedContributor.getId());
        assertThat(findContributor).isPresent();
        assertThat(findContributor.get().getId()).isEqualTo(savedContributor.getId());
    }
}