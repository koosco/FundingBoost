package practice.fundingboost2.item.funding.app.dto;

import java.time.LocalDateTime;
import practice.fundingboost2.item.funding.repo.entity.Funding;
import practice.fundingboost2.item.funding.repo.entity.FundingStatus;
import practice.fundingboost2.item.funding.repo.entity.FundingTag;

public record GetFundingInfoResponseDto(
    Long fundingId,
    String message,
    FundingTag tag,
    int totalPrice,
    int collectPrice,
    LocalDateTime deadLine,
    FundingStatus fundingStatus) {

    public static GetFundingInfoResponseDto from(Funding funding) {
        return new GetFundingInfoResponseDto(funding.getId(), funding.getMessage(), funding.getTag(),
            funding.getTotalPrice(),
            funding.getCollectPrice(), funding.getDeadLine(), funding.getStatus());
    }
}
