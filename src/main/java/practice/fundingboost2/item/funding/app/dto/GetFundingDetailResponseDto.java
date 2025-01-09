package practice.fundingboost2.item.funding.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import practice.fundingboost2.item.item.app.dto.GetItemResponseDto;
import practice.fundingboost2.member.app.dto.GetFundingParticipantDto;

@Schema(description = "펀딩 상세 조회 응답 Dto")
public record GetFundingDetailResponseDto(

    @Schema(description = "펀딩 정보")
    GetFundingResponseDto getFundingInfoResponseDto,

    @Schema(description = "펀딩 상품 목록")
    List<GetItemResponseDto> getFundingItemResponseDtos,

    @Schema(description = "펀딩 참여자 목록")
    List<GetFundingParticipantDto> getFundingParticipantDtos) {

}
