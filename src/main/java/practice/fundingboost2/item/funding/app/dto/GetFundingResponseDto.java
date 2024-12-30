package practice.fundingboost2.item.funding.app.dto;

import java.time.LocalDateTime;
import java.util.List;
import practice.fundingboost2.item.funding.repo.entity.Funding;
import practice.fundingboost2.item.funding.repo.entity.FundingStatus;
import practice.fundingboost2.item.funding.repo.entity.FundingTag;
import practice.fundingboost2.member.app.dto.GetMemberInfoDto;

public record GetFundingResponseDto(
    Long fundingId,
    String message,
    FundingTag tag,
    Integer totalPrice,
    Integer collectPrice,
    LocalDateTime createdAt,
    LocalDateTime deadLine,
    FundingStatus fundingStatus,
    GetMemberInfoDto memberInfo,
    List<GetFundingItemResponseDto> fundingItems
) {

    public static GetFundingResponseDto from(Funding funding) {
        return new GetFundingResponseDto(
            funding.getId(),
            funding.getMessage(),
            funding.getTag(),
            funding.getTotalPrice(),
            funding.getCollectPrice(),
            funding.getCreatedAt(),
            funding.getDeadLine(),
            funding.getStatus(),
            GetMemberInfoDto.from(funding.getMember()),
            funding.getFundingItems().stream()
                .map(GetFundingItemResponseDto::from)
                .toList());
    }
}
