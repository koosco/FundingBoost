package practice.fundingboost2.item.funding.app.dto;

import java.time.LocalDateTime;
import java.util.List;

public record CreateFundingRequestDto(List<CreateFundingItemRequestDto> items,
                                      LocalDateTime deadline,
                                      String tag,
                                      String message) {

}
