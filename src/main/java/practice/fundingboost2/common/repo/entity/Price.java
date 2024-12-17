package practice.fundingboost2.common.repo.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.common.exception.ErrorCode;

@Getter
@Embeddable
@EqualsAndHashCode(of = {"price"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Price {

    public static final Price ZERO = new Price(0);

    @ColumnDefault("0")
    private Integer price;

    public Price(Integer price) {
        if (price < 0) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }
        this.price = price;
    }

    public Price add(Price price) {
        return new Price(this.price + price.price);
    }
}
