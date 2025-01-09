package practice.fundingboost2.member.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import practice.fundingboost2.item.funding.repo.entity.Contributor;

@Schema(description = "펀딩 참여자 Dto")
public record GetFundingParticipantDto(

    @Schema(
        description = "참여자 ID",
        example = "1"
    )
    @NotNull
    Long memberId,

    @Schema(
        description = "참여자 닉네임",
        example = "홍길동"
    )
    @NotBlank
    String memberName,

    @Schema(
        description = "참여자 이미지 URL",
        example = "https://example.com"
    )
    @Nullable
    String memberImageUrl,

    @Schema(
        description = "참여 금액",
        example = "10000"
    )
    @NotNull
    @Min(0)
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
