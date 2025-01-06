package practice.fundingboost2.item.funding.app.dto;

import java.time.LocalDateTime;

public record UpdateFundingRequestDto(
    Long friendId,
    LocalDateTime deadline,
    String fundingStatus,
    Integer fundPrice) {

}
