package practice.fundingboost2.item.funding.app.dto;

import java.util.List;
import practice.fundingboost2.member.app.dto.GetFundingParticipantDto;

public record GetFundingResponseDto(GetFundingInfoResponseDto getFundingInfoResponseDto, List<GetFundingItemResponseDto> getFundingItemResponseDtos, List<GetFundingParticipantDto> getFundingParticipantDtos) {

}
