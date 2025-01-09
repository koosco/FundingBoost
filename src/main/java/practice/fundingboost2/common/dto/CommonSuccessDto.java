package practice.fundingboost2.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "API 성공 응답 Dto")
public record CommonSuccessDto(

    @Schema(description = "성공 여부")
    boolean isSuccess) {

  public static CommonSuccessDto fromEntity(boolean success) {
    return CommonSuccessDto.builder().isSuccess(success).build();
  }
}
