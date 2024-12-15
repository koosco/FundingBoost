package practice.fundingboost2.member.app.dto;

public record UpdateMemberRequestDto(String nickname, String imageUrl, String phoneNumber) {

    public static UpdateMemberRequestDto of(String nickname, String email, String phoneNumber) {
        return new UpdateMemberRequestDto(nickname, email, phoneNumber);
    }

}
