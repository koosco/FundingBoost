package practice.fundingboost2.member.app.dto;

public record UpdateMemberRequestDto(String nickname, String phoneNumber) {

    public static UpdateMemberRequestDto of(String nickname, String phoneNumber) {
        return new UpdateMemberRequestDto(nickname, phoneNumber);
    }

}
