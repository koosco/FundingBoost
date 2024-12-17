package practice.fundingboost2.item.item.ui.dto;

import java.util.List;
import practice.fundingboost2.item.item.repo.entity.Item;

public record GetItemDetailResponseDto(Long itemId, String itemName, String brand, int price, String itemImageUrl,
                                       String category, int reviewCount, int likeCount, boolean isLiked,
                                       List<GetOptionResponseDto> options) {

    public static GetItemDetailResponseDto from(Item item, boolean isLiked, List<GetOptionResponseDto> options) {
        return new GetItemDetailResponseDto(item.getId(), item.getName(), item.getBrand(), item.getPrice(),
            item.getImageUrl(), item.getCategory(), item.getReviewCount(), item.getLikeCount(), isLiked, options);
    }

}
