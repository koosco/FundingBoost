package practice.fundingboost2.item.item.app.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetOptionResponseDto{

    private Long optionId;
    private String optionName;
    private int quantity;
}
