package practice.fundingboost2.item.funding.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.fundingboost2.config.security.annotation.Auth;
import practice.fundingboost2.item.funding.repo.FundingQueryRepository;
import practice.fundingboost2.item.funding.ui.dto.GetFundingListResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/funding")
public class FundingQueryController {

    final FundingQueryRepository fundingQueryRepository;

    @GetMapping
    public GetFundingListResponseDto getFundings(@Auth Long memberId) {
        return fundingQueryRepository.findFundings(memberId);
    }
}
