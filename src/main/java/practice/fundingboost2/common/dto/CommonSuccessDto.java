package practice.fundingboost2.common.dto;

import lombok.Builder;

@Builder
public record CommonSuccessDto(boolean isSuccess) {

  public static CommonSuccessDto fromEntity(boolean success) {
    return CommonSuccessDto.builder().isSuccess(success).build();
  }
}
