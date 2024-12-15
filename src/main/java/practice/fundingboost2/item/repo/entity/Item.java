package practice.fundingboost2.item.repo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(name = "brand_name", length = 100)
    private String brandName;

    @Column(name = "category", length = 100)
    private String category;

    @Column(name = "option_name", length = 100)
    private String optionName;
}
