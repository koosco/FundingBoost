package practice.fundingboost2.item.gifthub.repo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.common.exception.ErrorCode;

@Getter
@Entity
@NoArgsConstructor
public class Gifthub {

    private static final Integer DEFAULT_QUANTITY = 1;

    @Getter
    @EmbeddedId
    private GifthubId id;

    @Column(nullable = false)
    private Integer quantity;

    public Gifthub(GifthubId id) {
        this.id = id;
        this.quantity = DEFAULT_QUANTITY;
    }

    public void updateQuantity(Integer quantity) {
        validateQuantity(quantity);
        this.quantity = quantity;
    }

    private static void validateQuantity(Integer quantity) {
        if (quantity <= 0) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }
    }

    public Long getItemId() {
        return id.getItemId();
    }
}
