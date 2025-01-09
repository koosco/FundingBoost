package practice.fundingboost2.item.funding.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import java.time.LocalDateTime;

@Schema(description = "펀딩 수정 요청 Dto")
public record UpdateFundingRequestDto(

    @Schema(
        description = "펀딩 ID",
        example = "1"
    )
    @Nullable
    Long friendId,

    @Schema(
        description = "펀딩 태그",
        example = "BIRTHDAY"
    )
    @Nullable
    LocalDateTime deadline,

    @Schema(
        description = "펀딩 상태",
        example = "PROGRESS"
    )
    @Nullable
    String fundingStatus,

    @Schema(
        description = "펀딩 모금액",
        example = "50000"
    )
    @Nullable
    Integer fundPrice) {

}
