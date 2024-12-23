package practice.fundingboost2.item.funding.ui;

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
import practice.fundingboost2.item.funding.app.dto.UpdateFundingRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/funding")
public class FundingController {

    private final FundingService fundingService;

    @PostMapping
    public ResponseDto<CommonSuccessDto> createFunding(@Auth Long memberId, @RequestBody CreateFundingRequestDto dto) {
        return ResponseDto.created(fundingService.createFunding(memberId, dto));
    }

    @PatchMapping("/{funding_id}")
    public ResponseDto<CommonSuccessDto> updateFunding(@Auth Long memberId, @PathVariable("funding_id") Long fundingId,
        @RequestBody UpdateFundingRequest dto) {
        return ResponseDto.ok(fundingService.updateFunding(memberId, fundingId, dto));
    }
}
