package practice.fundingboost2.item.gifthub.repo.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class GifthubId {

    private Long memberId;
    private Long itemId;
    private Long optionId;
}
