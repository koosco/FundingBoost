package practice.fundingboost2.item.funding.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import practice.fundingboost2.item.funding.repo.entity.Funding;
import practice.fundingboost2.item.funding.repo.entity.FundingItem;
import practice.fundingboost2.item.funding.repo.entity.FundingStatus;
import practice.fundingboost2.item.funding.repo.entity.FundingTag;
import practice.fundingboost2.member.app.dto.GetMemberInfoDto;

@Schema(description = "펀딩 조회 응답 Dto")
public record GetFundingResponseDto(

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
    Integer totalPrice,

    @Schema(
        description = "펀딩 모금액",
        example = "50000")
    @NotNull
    Integer collectPrice,

    @Schema(
        description = "펀딩 생성일",
        example = "2021-08-31T00:00:00")
    @NotNull
    LocalDateTime createdAt,

    @Schema(
        description = "펀딩 마감일",
        example = "2021-08-31T00:00:00")
    @NotNull
    LocalDateTime deadLine,

    @Schema(
        description = "펀딩 상태",
        example = "ON_GOING")
    @NotNull
    FundingStatus fundingStatus,

    @Schema(
        description = "펀딩 참여자 정보")
    @Nullable
    GetMemberInfoDto memberInfo,
    List<GetFundingItemResponseDto> fundingItems,

    @Schema(
        description = "펀딩 참여자 수",
        example = "10")
    @Nullable
    Integer contributorCount
) {

    public static GetFundingResponseDto getDto(Funding funding) {
        return new GetFundingResponseDto(
            funding.getId(),
            funding.getMessage(),
            funding.getTag(),
            funding.getTotalPrice(),
            funding.getCollectPrice(),
            funding.getCreatedAt(),
            funding.getDeadLine(),
            funding.getStatus(),
            GetMemberInfoDto.from(funding.getMember()),
            funding.getFundingItems().stream()
                .sorted(Comparator.comparingInt(FundingItem::getSequence))
                .map(GetFundingItemResponseDto::from)
                .toList(),
            null);
    }

    public static GetFundingResponseDto getHistory(Funding funding) {
        return new GetFundingResponseDto(
            funding.getId(),
            null,
            funding.getTag(),
            funding.getTotalPrice(),
            funding.getCollectPrice(),
            funding.getCreatedAt(),
            funding.getDeadLine(),
            null,
            null,
            null,
            funding.getFundingCount());
    }
}
