package practice.fundingboost2.item.item.repo.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Entity
@DynamicUpdate
@Table(name = "item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name", length = 100)
    private String name;

    @Min(0)
    @Column(name = "item_price")
    @ColumnDefault("0")
    private Integer price;

    @Column(name = "item_image_url")
    private String imageUrl;

    @Column(name = "brand", length = 100)
    private String brand;

    @Column(name = "category", length = 100)
    private String category;

    @ColumnDefault("0")
    private Integer reviewCount;

    @ColumnDefault("0")
    private Integer likeCount;

    @ColumnDefault("0")
    private Integer fundingCount;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Option> options = new ArrayList<>();

    public Item(String name, int price, String imageUrl, String brand, String category) {
        this.name = name;
        this.price = 0;
        this.imageUrl = imageUrl;
        this.brand = brand;
        this.category = category;
        this.reviewCount = 0;
        this.likeCount = 0;
        this.fundingCount = 0;
    }

    public Bookmark mark(BookmarkId bookmarkId) {
        this.likeCount++;
        return new Bookmark(bookmarkId);
    }

    public void unmark() {
        this.likeCount--;
    }

    public void fund() {
        this.fundingCount++;
    }
}
