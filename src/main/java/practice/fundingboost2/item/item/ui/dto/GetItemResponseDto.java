package practice.fundingboost2.item.item.ui.dto;

import lombok.Data;
import practice.fundingboost2.item.item.repo.entity.Item;

@Data
public class GetItemResponseDto {
    private Long itemId;
    private String itemName;
    private String brand;
    private int price;
    private String itemImageUrl;
    private String category;
    private int reviewCount;
    private int likeCount;
    private int fundingCount;

    public GetItemResponseDto(Item item) {
        this.itemId = item.getId();
        this.itemName = item.getName();
        this.brand = item.getBrand();
        this.price = item.getPrice();
        this.itemImageUrl = item.getImageUrl();
        this.category = item.getCategory();
        this.reviewCount = item.getReviewCount();
        this.likeCount = item.getLikeCount();
        this.fundingCount = item.getFundingCount();
    }
}
