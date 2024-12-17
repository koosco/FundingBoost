package practice.fundingboost2.item.item.repo.entity;

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
    private int price;

    @Column(name = "item_image_url")
    private String imageUrl;

    @Column(name = "brand", length = 100)
    private String brand;

    @Column(name = "category", length = 100)
    private String category;

    private int reviewCount;

    private int likeCount;

    @OneToMany(mappedBy = "item")
    private List<Option> options = new ArrayList<>();

    public Item(String name, int price, String imageUrl, String brand, String category, int reviewCount,
        int likeCount) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.brand = brand;
        this.category = category;
        this.reviewCount = reviewCount;
        this.likeCount = likeCount;
    }

    public void mark() {
        this.likeCount++;
    }

    public void unmark() {
        this.likeCount--;
    }
}
