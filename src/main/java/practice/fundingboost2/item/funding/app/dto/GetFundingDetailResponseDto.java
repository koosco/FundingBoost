package practice.fundingboost2.item.funding.app.dto;

import java.util.List;
import practice.fundingboost2.item.item.app.dto.GetItemResponseDto;
import practice.fundingboost2.member.app.dto.GetFundingParticipantDto;

public record GetFundingDetailResponseDto(GetFundingInfoResponseDto getFundingInfoResponseDto,
                                          List<GetItemResponseDto> getFundingItemResponseDtos,
                                          List<GetFundingParticipantDto> getFundingParticipantDtos) {

}
