package practice.fundingboost2.item.funding.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.item.funding.app.dto.UpdateFundingRequestDto;
import practice.fundingboost2.item.funding.app.interfaces.FundingRepository;
import practice.fundingboost2.item.funding.repo.entity.Funding;
import practice.fundingboost2.member.app.MemberService;
import practice.fundingboost2.member.repo.entity.Member;

@ExtendWith(MockitoExtension.class)
public class FundingServiceUpdateTest {

    @InjectMocks
    FundingService fundingService;

    @Mock
    FundingRepository fundingRepository;

    @Mock
    MemberService memberService;

    @Mock
    Funding funding;

    @Mock
    Member member;

    @Test
    void givenMemberAndFunding_whenUpdate_thenReturnSuccessDto() {
        // given
        doReturn(funding).when(fundingRepository).findById(any());
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
}
