package practice.fundingboost2.item.funding.app;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.item.funding.app.dto.CreateFundingRequestDto;
import practice.fundingboost2.item.funding.app.dto.GetFundingDetailResponseDto;
import practice.fundingboost2.item.funding.app.dto.GetFundingInfoResponseDto;
import practice.fundingboost2.item.funding.app.dto.GetFundingResponseDto;
import practice.fundingboost2.item.funding.app.dto.UpdateFundingRequest;
import practice.fundingboost2.item.funding.app.interfaces.FundingRepository;
import practice.fundingboost2.item.funding.repo.entity.Funding;
import practice.fundingboost2.item.funding.repo.entity.FundingItem;
import practice.fundingboost2.item.funding.repo.jpa.ContributorRepository;
import practice.fundingboost2.item.item.app.ItemService;
import practice.fundingboost2.item.item.app.dto.GetItemResponseDto;
import practice.fundingboost2.item.item.repo.OptionRepository;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.repo.entity.Option;
import practice.fundingboost2.member.app.MemberService;
import practice.fundingboost2.member.app.dto.GetFundingParticipantDto;
import practice.fundingboost2.member.repo.entity.Member;

@Service
@Transactional
@RequiredArgsConstructor
public class FundingService {

    private final FundingRepository fundingRepository;
    private final ItemService itemService;
    private final MemberService memberService;
    private final OptionRepository optionRepository;
    private final ContributorRepository contributorRepository;


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

    public CommonSuccessDto updateFunding(Long memberId, Long fundingId, UpdateFundingRequest dto) {
        Member member = memberService.findMember(memberId);
        Funding funding = fundingRepository.findFunding(fundingId);
        funding.validateMember(member);

        funding.update(dto.deadline(), dto.fundingStatus());

        return CommonSuccessDto.fromEntity(true);
    }

    public GetFundingDetailResponseDto getFunding(Long memberId, Long fundingId) {
        Member member = memberService.findMember(memberId);
        Funding funding = fundingRepository.findFunding(fundingId);
        funding.validateMember(member);

        GetFundingInfoResponseDto getFundingInfoResponseDto = GetFundingInfoResponseDto.from(funding);
        List<GetItemResponseDto> getFundingItemResponseDtos = funding.getFundingItems().stream()
            .map(GetItemResponseDto::from).toList();
        List<GetFundingParticipantDto> getFundingParticipantDtos = contributorRepository.findAll_ByFundingId(fundingId)
            .stream().map(GetFundingParticipantDto::from).toList();

        return new GetFundingDetailResponseDto(getFundingInfoResponseDto, getFundingItemResponseDtos,
            getFundingParticipantDtos);
    }

    public Page<GetFundingResponseDto> getFundings(Long memberId, Pageable pageable) {
        return fundingRepository.findFundings(memberId, pageable);
    }
}
