package practice.fundingboost2.item.funding.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "펀딩 생성 요청 Dto")
public record CreateFundingRequestDto(

    @Schema(
        description = "펀딩 상품 목록"
    )
    @NotEmpty
    List<CreateFundingItemRequestDto> items,

    @Schema(
        description = "펀딩 마감일",
        example = "2021-08-31T00:00:00"
    )
    @NotNull
    LocalDateTime deadline,

    @Schema(
        description = "펀딩 태그",
        example = "BIRTHDAY"
    )
    @NotBlank
    String tag,

    @Schema(
        description = "펀딩 메세지",
        example = "생일 축하해주세요"
    )
    @NotBlank
    String message) {

}
