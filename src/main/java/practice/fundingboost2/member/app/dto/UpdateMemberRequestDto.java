package practice.fundingboost2.member.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;

@Schema(description = "회원 정보 수정 요청 Dto")
public record UpdateMemberRequestDto(

    @Schema(
        description = "닉네임",
        example = "닉네임"
    )
    @Nullable
    String nickname,

    @Schema(
        description = "전화번호",
        example = "010-1234-5678")
    @Nullable
    String phoneNumber) {

    public static UpdateMemberRequestDto of(String nickname, String phoneNumber) {
        return new UpdateMemberRequestDto(nickname, phoneNumber);
    }

}
