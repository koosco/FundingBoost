package practice.fundingboost2.item.funding.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.fundingboost2.item.funding.repo.querydsl.FundingQueryRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/funding")
public class FundingQueryController {

    final FundingQueryRepository fundingQueryRepository;


}
