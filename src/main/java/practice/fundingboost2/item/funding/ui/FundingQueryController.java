package practice.fundingboost2.item.funding.ui;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.fundingboost2.common.dto.ResponseDto;
import practice.fundingboost2.config.security.annotation.Auth;
import practice.fundingboost2.item.funding.app.FundingQueryService;
import practice.fundingboost2.item.funding.app.dto.GetFundingDetailResponseDto;
import practice.fundingboost2.item.funding.app.dto.GetFundingResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/funding")
public class FundingQueryController {

    private final FundingQueryService fundingService;

    @Operation(summary = "펀딩 조회", description = "펀딩을 조회합니다.")
    @GetMapping("/{funding_id}")
    public ResponseDto<GetFundingDetailResponseDto> getFunding(
        @Auth
        Long memberId,

        @PathVariable("funding_id")
        Long fundingId) {
        return ResponseDto.ok(fundingService.getFunding(memberId, fundingId));
    }

    @Operation(summary = "펀딩 내역 조회", description = "펀딩 내역을 조회합니다.")
    @GetMapping("/history")
    public ResponseDto<List<GetFundingResponseDto>> getFundingHistory(
        @Auth
        Long memberId) {
        return ResponseDto.ok(fundingService.getFundingHistory(memberId));
    }

    @Operation(summary = "펀딩 목록 조회", description = "펀딩 목록을 조회합니다.")
    @GetMapping
    public ResponseDto<Page<GetFundingResponseDto>> getFundings(
        @Auth
        Long memberId,

        Pageable pageable) {
        return ResponseDto.ok(fundingService.getFundings(memberId, pageable));
    }
}
