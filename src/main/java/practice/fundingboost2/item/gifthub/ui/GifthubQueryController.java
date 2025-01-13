package practice.fundingboost2.item.gifthub.ui;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.fundingboost2.common.dto.ResponseDto;
import practice.fundingboost2.config.security.annotation.Auth;
import practice.fundingboost2.item.gifthub.app.dto.GetGifthubResponseDto;
import practice.fundingboost2.item.gifthub.repo.GifthubQueryRepository;

@RestController
@RequestMapping("/api/gifthub")
@RequiredArgsConstructor
public class GifthubQueryController {

    private final GifthubQueryRepository gifthubQueryRepository;

    @Operation(summary = "선물함 조회", description = "회원의 선물함을 조회합니다.")
    @GetMapping
    public ResponseDto<Page<GetGifthubResponseDto>> getGifthub(
        @Auth
        Long memberId,

        Pageable pageable) {
        return ResponseDto.ok(gifthubQueryRepository.getAllGifthub(memberId, pageable));
    }
}
