package practice.fundingboost2.member.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import practice.fundingboost2.member.repo.entity.Member;

@Schema(description = "회원 정보 응답 Dto")
public record GetMemberResponseDto(

    @Schema(
        description = "회원 ID",
        example = "1"
    )
    @NotNull
    Long id,

    @Schema(
        description = "회원 닉네임",
        example = "홍길동"
    )
    @NotBlank
    String nickname,

    @Schema(
        description = "회원 이메일",
        example = "rnxogud123@naver.com")
    @Nullable
    String email,

    @Schema(
        description = "회원 이미지 URL",
        example = "https://example.com"
    )
    @Nullable
    String imageUrl,

    @Schema(
        description = "포인트",
        example = "10000"
    )
    @NotNull
    int point,

    @Schema(
        description = "전화번호",
        example = "010-1234-5678"
    )
    @Nullable
    String phoneNumber) {

    public static GetMemberResponseDto from(Member member) {
        return new GetMemberResponseDto(member.getId(), member.getNickname(), member.getEmail(), member.getImageUrl(),
            member.getPoint(), member.getPhoneNumber());
    }
}
