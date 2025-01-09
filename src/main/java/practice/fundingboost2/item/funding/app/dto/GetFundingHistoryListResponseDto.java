package practice.fundingboost2.item.funding.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "펀딩 내역 조회 응답 Dto")
public record GetFundingHistoryListResponseDto(

    @Schema(description = "펀딩 내역 목록")
    List<GetFundingHistoryResponseDto> fundingHistoryDtos) {
}
