package practice.fundingboost2.item.gifthub.repo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Gifthub {

    @Getter
    @EmbeddedId
    private GifthubId id;

    @Embedded
    @Column(nullable = false)
    private Quantity quantity;

    public Gifthub(GifthubId id) {
        this.id = id;
        this.quantity = new Quantity();
    }

    public void updateQuantity(Integer quantity) {
        this.quantity = new Quantity(quantity);
    }

    public Long getItemId() {
        return id.getItemId();
    }

    public Integer getQuantity() {
        return quantity.getQuantity();
    }
}
