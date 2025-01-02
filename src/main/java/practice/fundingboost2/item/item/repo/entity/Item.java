package practice.fundingboost2.item.item.repo.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import practice.fundingboost2.common.repo.entity.Price;

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

    @Column(name = "item_price")
    private Price price;

    @Column(name = "item_image_url")
    private String imageUrl;

    @Column(name = "brand", length = 100)
    private String brand;

    @Column(name = "category", length = 100)
    private String category;

    private Integer reviewCount;

    private Integer likeCount;

    private Integer fundingCount;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Option> options = new ArrayList<>();

    public Item(String name, int price, String imageUrl, String brand, String category) {
        this.name = name;
        this.price = new Price(price);
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

    public Integer getPrice() {
        return price.getPrice();
    }

    public Price getPriceEntity() {
        return price;
    }
}
