package practice.fundingboost2.item.funding.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import practice.fundingboost2.item.funding.repo.entity.Funding;
import practice.fundingboost2.item.funding.repo.entity.FundingStatus;
import practice.fundingboost2.item.funding.repo.entity.FundingTag;

@Schema(description = "펀딩 정보 조회 응답 Dto")
public record GetFundingInfoResponseDto(

    @Schema(
        description = "펀딩 ID",
        example = "1")
    @NotNull
    Long fundingId,

    @Schema(
        description = "펀딩 메세지",
        example = "생일 축하해주세요")
    @NotNull
    String message,

    @Schema(
        description = "펀딩 태그",
        example = "BIRTHDAY")
    @NotBlank
    FundingTag tag,

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
        description = "펀딩 마감일",
        example = "2021-08-31T00:00:00")
    @NotNull
    LocalDateTime deadLine,

    @Schema(
        description = "펀딩 상태",
        example = "ON_GOING")
    @NotNull
    FundingStatus fundingStatus) {

    public static GetFundingInfoResponseDto from(Funding funding) {
        return new GetFundingInfoResponseDto(funding.getId(), funding.getMessage(), funding.getTag(),
            funding.getTotalPrice(),
            funding.getCollectPrice(), funding.getDeadLine(), funding.getStatus());
    }
}
