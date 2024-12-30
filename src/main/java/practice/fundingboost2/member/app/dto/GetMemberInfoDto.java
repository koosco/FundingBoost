package practice.fundingboost2.member.app.dto;

import practice.fundingboost2.member.repo.entity.Member;

public record GetMemberInfoDto(
    Long memberId,
    String memberName,
    String memberImageUrl
) {

    public static GetMemberInfoDto from(Member member) {
        return new GetMemberInfoDto(member.getId(), member.getNickname(), member.getImageUrl());
    }
}
