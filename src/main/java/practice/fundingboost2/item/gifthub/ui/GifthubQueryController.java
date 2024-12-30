package practice.fundingboost2.item.gifthub.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.fundingboost2.common.dto.ResponseDto;
import practice.fundingboost2.config.security.annotation.Auth;
import practice.fundingboost2.item.gifthub.repo.GifthubQueryRepository;
import practice.fundingboost2.item.gifthub.ui.dto.GetGifthubResponseDto;

@RestController
@RequestMapping("/api/gifthub")
@RequiredArgsConstructor
public class GifthubQueryController {

    private final GifthubQueryRepository gifthubQueryRepository;

    @GetMapping
    public ResponseDto<Page<GetGifthubResponseDto>> getGifthub(@Auth Long memberId, Pageable pageable) {
        return ResponseDto.ok(gifthubQueryRepository.getAllGifthub(memberId, pageable));
    }
}
