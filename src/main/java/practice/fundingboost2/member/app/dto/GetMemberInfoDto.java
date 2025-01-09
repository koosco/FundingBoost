package practice.fundingboost2.member.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import practice.fundingboost2.member.repo.entity.Member;

@Schema(description = "회원 정보 응답 Dto")
public record GetMemberInfoDto(

    @Schema(
        description = "회원 ID",
        example = "1"
    )
    @NotNull
    Long memberId,

    @Schema(
        description = "회원 닉네임",
        example = "홍길동"
    )
    @NotBlank
    String memberName,

    @Schema(
        description = "회원 이미지 URL",
        example = "https://example.com"
    )
    @Nullable
    String memberImageUrl
) {

    public static GetMemberInfoDto from(Member member) {
        return new GetMemberInfoDto(member.getId(), member.getNickname(), member.getImageUrl());
    }
}
