package practice.fundingboost2.item.funding.app;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import practice.fundingboost2.item.funding.app.dto.GetFundingDetailResponseDto;
import practice.fundingboost2.item.funding.app.dto.GetFundingResponseDto;
import practice.fundingboost2.item.funding.app.interfaces.FundingRepository;
import practice.fundingboost2.item.funding.repo.entity.Funding;
import practice.fundingboost2.item.item.app.dto.GetItemResponseDto;
import practice.fundingboost2.member.app.MemberService;
import practice.fundingboost2.member.app.dto.GetFundingParticipantDto;
import practice.fundingboost2.member.repo.entity.Member;

@Service
@RequiredArgsConstructor
public class FundingQueryService {

    private final FundingRepository fundingRepository;
    private final MemberService memberService;
    private final ContributorService contributorService;

    public Funding findFunding(Long fundingId) {
        return fundingRepository.findById(fundingId);
    }

    public Funding concurrentFindFunding(Long fundingId) {
        return fundingRepository.concurrentFindFunding(fundingId);
    }

    public GetFundingDetailResponseDto getFunding(Long memberId, Long fundingId) {
        Member member = memberService.findMember(memberId);
        Funding funding = findFunding(fundingId);
        funding.validateMember(member);

        GetFundingResponseDto getFundingInfoResponseDto = GetFundingResponseDto.getDetail(funding);
        List<GetItemResponseDto> getFundingItemResponseDtos = funding.getFundingItems().stream()
            .map(GetItemResponseDto::from)
            .toList();

        List<GetFundingParticipantDto> getFundingParticipantDtos = contributorService.findAllByFundingId(fundingId)
            .stream()
            .map(GetFundingParticipantDto::from)
            .toList();

        return new GetFundingDetailResponseDto(getFundingInfoResponseDto, getFundingItemResponseDtos,
            getFundingParticipantDtos);
    }

    public Page<GetFundingResponseDto> getFundings(Long memberId, Pageable pageable) {
        return fundingRepository.findFundings(memberId, pageable);
    }

    public List<GetFundingResponseDto> getFundingHistory(Long memberId) {
        return fundingRepository
            .findAllByMemberId(memberId).stream()
            .map(GetFundingResponseDto::getHistory)
            .toList();
    }
}
