package practice.fundingboost2.member.app.dto;

import practice.fundingboost2.item.funding.repo.entity.Contributor;

public record GetFundingParticipantDto(
    Long memberId,
    String memberName,
    String memberImageUrl,
    int price) {

    public static GetFundingParticipantDto from(Contributor contributor) {
        return new GetFundingParticipantDto(
            contributor.getMember().getId(),
            contributor.getMember().getNickname(),
            contributor.getMember().getImageUrl(),
            contributor.getContribution()
        );
    }
}
