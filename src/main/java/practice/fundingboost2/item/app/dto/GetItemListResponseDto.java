package practice.fundingboost2.item.app.dto;

import java.util.List;
import lombok.Data;
import practice.fundingboost2.item.repo.entity.Item;

@Data
public class GetItemListResponseDto {

    private final List<GetItemResponseDto> items;

    public GetItemListResponseDto(List<Item> items) {
        this.items = items.stream()
            .map(GetItemResponseDto::new)
            .toList();
    }
}
