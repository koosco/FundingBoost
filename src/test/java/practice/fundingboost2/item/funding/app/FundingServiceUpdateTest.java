package practice.fundingboost2.item.funding.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.item.funding.app.dto.UpdateFundingRequestDto;
import practice.fundingboost2.item.funding.repo.entity.Funding;
import practice.fundingboost2.member.app.MemberService;
import practice.fundingboost2.member.repo.entity.Member;

@ExtendWith(MockitoExtension.class)
public class FundingServiceUpdateTest {

    @InjectMocks
    FundingService fundingService;

    @Mock
    FundingQueryService queryService;

    @Mock
    MemberService memberService;

    @Mock
    Funding funding;

    @Mock
    Member member;

    @Mock
    ContributorService contributorService;

    @Mock
    Member friend;

    final Long friendId = 1L;

    @Test
    void givenMemberAndFunding_whenUpdate_thenReturnSuccessDto() {
        // given
        doReturn(funding).when(queryService).findFunding(any());
        doReturn(member).when(memberService).findMember(any());
        doNothing().when(funding).validateMember(any());
        UpdateFundingRequestDto requestDto = new UpdateFundingRequestDto(
            null,
            null,
            "FAILED",
            null);

        // when
        CommonSuccessDto responseDto = fundingService.updateFunding(1L, 1L, requestDto);

        // then
        assertThat(responseDto.isSuccess()).isTrue();
    }

    @Test
    void givenFunding_whenFriendFund_thenFriendPointMustDecrease() {
        // given
        doReturn(funding).when(queryService).concurrentFindFunding(any());
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
        doReturn(funding).when(queryService).concurrentFindFunding(any());
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
