package practice.fundingboost2.item.gifthub.repo.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import practice.fundingboost2.item.item.repo.entity.Item;

@Getter
@Entity
@NoArgsConstructor
public class Gifthub {

    @EmbeddedId
    private GifthubId id;

    @ColumnDefault("1")
    private Integer quantity;

    public Gifthub(GifthubId id, Item item) {
        this.id = id;
        this.quantity = 1;
    }

    public void updateQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getItemId() {
        return id.getItemId();
    }
}
