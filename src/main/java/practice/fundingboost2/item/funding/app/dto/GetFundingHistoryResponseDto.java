package practice.fundingboost2.item.funding.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import practice.fundingboost2.item.funding.repo.entity.Funding;
import practice.fundingboost2.item.funding.repo.entity.FundingTag;

@Schema(description = "펀딩 내역 조회 응답 Dto")
public record GetFundingHistoryResponseDto(

    @Schema(
        description = "펀딩 ID",
        example = "1")
    @NotNull
    Long fundingId,

    @Schema(
        description = "펀딩 태그",
        example = "BIRTHDAY")
    @NotBlank
    FundingTag fundingTag,

    @Schema(
        description = "펀딩 생성일",
        example = "2021-08-31T00:00:00")
    @NotNull
    LocalDateTime fundingStart,

    @Schema(
        description = "펀딩 마감일",
        example = "2021-08-31T00:00:00")
    @NotNull
    LocalDateTime fundingDeadline,

    @Schema(
        description = "펀딩 총 금액",
        example = "100000")
    @NotNull
    int totalPrice,

    @Schema(
        description = "펀딩 모금액",
        example = "50000")
    @NotNull
    int collectPrice,

    @Schema(
        description = "펀딩 참여자 수",
        example = "10")
    @NotNull
    int contributorCount) {

    public static GetFundingHistoryResponseDto from(Funding funding) {
        return new GetFundingHistoryResponseDto(funding.getId(), funding.getTag(), funding.getCreatedAt(),
            funding.getDeadLine(), funding.getTotalPrice(), funding.getCollectPrice(), funding.getFundingCount());
    }
}
