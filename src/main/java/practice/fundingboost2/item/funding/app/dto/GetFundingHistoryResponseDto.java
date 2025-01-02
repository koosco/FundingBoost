package practice.fundingboost2.item.funding.app.dto;

import java.time.LocalDateTime;
import practice.fundingboost2.item.funding.repo.entity.Funding;
import practice.fundingboost2.item.funding.repo.entity.FundingTag;

public record GetFundingHistoryResponseDto(Long fundingId, FundingTag fundingTag, LocalDateTime fundingStart, LocalDateTime fundingDeadline, int totalPrice, int collectPrice,
                                           int contributorCount) {
    public static GetFundingHistoryResponseDto from(Funding funding, int contributorCount){
        return new GetFundingHistoryResponseDto(funding.getId(), funding.getTag(), funding.getCreatedAt(),
                funding.getDeadLine(), funding.getTotalPrice(), funding.getCollectPrice(), contributorCount);
    }
}
