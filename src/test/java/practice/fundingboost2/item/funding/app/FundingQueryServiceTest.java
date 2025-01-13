package practice.fundingboost2.item.funding.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import practice.fundingboost2.item.funding.app.dto.GetFundingDetailResponseDto;
import practice.fundingboost2.item.funding.app.interfaces.FundingRepository;
import practice.fundingboost2.item.funding.repo.entity.Funding;
import practice.fundingboost2.member.app.MemberService;
import practice.fundingboost2.member.repo.entity.Member;

@ExtendWith(MockitoExtension.class)
public class FundingQueryServiceTest {

    @InjectMocks
    FundingQueryService fundingService;

    @Mock
    FundingRepository fundingRepository;

    @Mock
    MemberService memberService;

    @Mock
    ContributorService contributorService;

    @Mock
    Funding funding;

    final static Long fundingId = 1L;

    @Mock
    Member member;

    final static Long memberId = 1L;


    @Test
    void whenFindFunding_thenFundingRepositoryCalled() {
        // given
        // when
        fundingService.findFunding(fundingId);

        // then
        verify(fundingRepository).findById(fundingId);
    }

    @Test
    void whenFindConcurrentFunding_thenFundingRepositoryCalled() {
        // given
        // when
        fundingService.concurrentFindFunding(fundingId);

        // then
        verify(fundingRepository).concurrentFindFunding(fundingId);
    }
    
    @Test
    void givenMemberAndFunding_whenGetFunding_thenReturnDto() {
        // given
        doReturn(member).when(memberService).findMember(memberId);
        doReturn(funding).when(fundingRepository).findById(fundingId);
        // when
        GetFundingDetailResponseDto responseDto = fundingService.getFunding(memberId, fundingId);

        // then
        assertThat(responseDto).isNotNull();
        verify(memberService).findMember(1L);
        verify(fundingRepository).findById(1L);
        verify(contributorService).findAllByFundingId(1L);
    }
    
    @Test
    void givenMemberId_whenGetFundings_thenFundingRepositoryCalled() {
        // given
        // when
        fundingService.getFundings(memberId, null);

        // then
        verify(fundingRepository, times(1)).findFundings(any(), any());
    }
    
    @Test
    void givenMemberId_whenGetFundingHistory_thenFundingRepositoryCalled() {
        // given
        // when
        fundingService.getFundingHistory(memberId);

        // then
        verify(fundingRepository, times(1)).findAllByMemberId(any());
    }
}
