package practice.fundingboost2.common.repo.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.common.exception.ErrorCode;

@Getter
@Embeddable
public class Price {

    private static final Integer ZERO = 0;

    @ColumnDefault("0")
    private Integer price;

    public Price(Integer price) {
        if (price < 0) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }
        this.price = price;
    }

    public Price() {
        this(ZERO);
    }
}
