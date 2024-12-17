package practice.fundingboost2.item.gifthub.ui.dto;

import java.util.List;

public record GetGifthubListResponseDto(List<GetGifthubResponseDto> gifthubs) {

    public static GetGifthubListResponseDto from(List<GetGifthubResponseDto> gifthubs) {
        return new GetGifthubListResponseDto(gifthubs);
    }
}
