package practice.fundingboost2.item.funding.app;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import practice.fundingboost2.item.funding.app.dto.UpdateFundingRequestDto;
import practice.fundingboost2.item.funding.app.interfaces.FundingRepository;
import practice.fundingboost2.item.funding.repo.entity.Funding;
import practice.fundingboost2.member.app.MemberService;
import practice.fundingboost2.member.repo.entity.Member;

@ExtendWith(MockitoExtension.class)
public class FundingServiceFundTest {

    @InjectMocks
    FundingService fundingService;

    @Mock
    FundingRepository fundingRepository;

    @Mock
    MemberService memberService;

    @Mock
    ContributorService contributorService;

    @Mock
    FundingQueryService queryService;

    @Mock
    Funding funding;

    @Mock
    Member friend;
    final Long friendId = 1L;

    @BeforeEach
    void init() {
        doReturn(funding).when(queryService).concurrentFindFunding(any());
    }

    @Test
    void givenFunding_whenFriendFund_thenFriendPointMustDecrease() {
        // given
        doReturn(friend).when(memberService).findMember(any());
        UpdateFundingRequestDto requestDto = new UpdateFundingRequestDto(
            friendId,
            null,
            null,
            1000);
        // when
        fundingService.fund(1L, requestDto);

        // then
        verify(contributorService).save(any());
        verify(friend, times(1)).decreasePoint(any(Integer.class));
    }

    @Test
    void givenFunding_whenFriendFund_thenFundingMustBeFunded() {
        // given
        doReturn(friend).when(memberService).findMember(any());
        UpdateFundingRequestDto requestDto = new UpdateFundingRequestDto(friendId,
            null,
            null,
            1000);
        // when
        fundingService.fund(1L, requestDto);

        // then
        verify(contributorService).save(any());
        verify(funding, times(1)).fund(any(int.class));
    }
}
