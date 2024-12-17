package practice.fundingboost2.item.gifthub.repo.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.common.exception.ErrorCode;

@Getter
@Embeddable
public class Quantity {

    private static final Integer INITIAL_QUANTITY = 1;

    @ColumnDefault("1")
    private Integer quantity;

    public Quantity(Integer quantity) {
        if (quantity <= 0) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }
        this.quantity = quantity;
    }

    public Quantity() {
        this(INITIAL_QUANTITY);
    }
}
