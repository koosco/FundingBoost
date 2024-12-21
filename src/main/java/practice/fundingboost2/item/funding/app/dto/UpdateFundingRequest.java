package practice.fundingboost2.item.funding.app.dto;

import java.time.LocalDateTime;

public record UpdateFundingRequest(LocalDateTime deadline,
                                    String fundingStatus) {

}
