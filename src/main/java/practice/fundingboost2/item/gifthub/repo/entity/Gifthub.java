package practice.fundingboost2.item.gifthub.repo.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity
@NoArgsConstructor
public class Gifthub {

    @EmbeddedId
    private GifthubId id;

    @ColumnDefault("1")
    private Integer quantity;

    public Gifthub(GifthubId id) {
        this.id = id;
        this.quantity = 1;
    }

    public Gifthub(Long memberId, Long itemId) {
        this(new GifthubId(memberId, itemId));
    }
}
