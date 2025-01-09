package practice.fundingboost2.item.funding.ui;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.common.dto.ResponseDto;
import practice.fundingboost2.config.security.annotation.Auth;
import practice.fundingboost2.item.funding.app.FundingService;
import practice.fundingboost2.item.funding.app.dto.CreateFundingRequestDto;
import practice.fundingboost2.item.funding.app.dto.UpdateFundingRequestDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/funding")
public class FundingController {

    private final FundingService fundingService;

    @Operation(summary = "펀딩 생성", description = "펀딩을 생성합니다.")
    @PostMapping
    public ResponseDto<CommonSuccessDto> createFunding(
        @Auth
        Long memberId,

        @RequestBody
        CreateFundingRequestDto dto) {
        return ResponseDto.created(fundingService.createFunding(memberId, dto));
    }

    @Operation(summary = "펀딩 참여", description = "펀딩에 참여합니다.")
    @PostMapping("/{funding_id}")
    public ResponseDto<CommonSuccessDto> fund(
        @PathVariable("funding_id")
        Long fundingId,

        @RequestBody
        UpdateFundingRequestDto dto) {
        return ResponseDto.ok(fundingService.fund(fundingId, dto));
    }

    @Operation(summary = "펀딩 수정", description = "펀딩을 수정합니다.")
    @PatchMapping("/{funding_id}")
    public ResponseDto<CommonSuccessDto> updateFunding(
        @Auth
        Long memberId,

        @PathVariable("funding_id")
        Long fundingId,

        @RequestBody
        UpdateFundingRequestDto dto) {
        return ResponseDto.ok(fundingService.updateFunding(memberId, fundingId, dto));
    }
}
