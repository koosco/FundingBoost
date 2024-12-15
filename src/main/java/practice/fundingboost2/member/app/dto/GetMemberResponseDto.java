package practice.fundingboost2.member.app.dto;

import practice.fundingboost2.member.repo.entity.Member;

public record GetMemberResponseDto(Long id, String nickname, String email, String imageUrl, int point, String phoneNumber) {

    public static GetMemberResponseDto from(Member member) {
        return new GetMemberResponseDto(member.getId(), member.getNickname(), member.getEmail(), member.getImageUrl(),
            member.getPoint().getPoint(), member.getPhoneNumber());
    }
}
