package practice.fundingboost2.item.funding.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.item.funding.app.dto.CreateFundingRequestDto;
import practice.fundingboost2.item.funding.repo.FundingRepository;
import practice.fundingboost2.item.funding.repo.entity.Funding;
import practice.fundingboost2.item.funding.repo.entity.FundingItem;
import practice.fundingboost2.item.item.app.ItemService;
import practice.fundingboost2.item.item.repo.OptionRepository;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.repo.entity.Option;
import practice.fundingboost2.member.app.MemberService;
import practice.fundingboost2.member.repo.entity.Member;

@Service
@Transactional
@RequiredArgsConstructor
public class FundingService {

    private final FundingRepository fundingRepository;
    private final ItemService itemService;
    private final MemberService memberService;
    private final OptionRepository optionRepository;

    public CommonSuccessDto createFunding(Long memberId, CreateFundingRequestDto dto) {
        Member member = memberService.findMember(memberId);

        Funding funding = new Funding(member, dto.message(), dto.tag(), dto.deadline());
        dto.items().forEach(
            item -> {
                Item findItem = itemService.findItem(item.itemId());
                Option findOption = optionRepository.findById(item.optionId())
                    .orElseThrow();
                new FundingItem(funding, findItem, findOption, item.sequence());
            });
        fundingRepository.save(funding);

        return CommonSuccessDto.fromEntity(true);
    }
}
