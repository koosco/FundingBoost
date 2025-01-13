package practice.fundingboost2.pay.ui;

import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.common.dto.ResponseDto;
import practice.fundingboost2.config.security.annotation.Auth;
import practice.fundingboost2.pay.app.PaymentService;
import practice.fundingboost2.pay.app.dto.PaymentRequestDto;

@Slf4j
@RestController
@RequestMapping("/api/pay")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "결제 준비", description = "결제 준비를 위한 상점 식별 번호를 반환합니다.")
    @GetMapping
    public ResponseDto<String> getMerchantUid() {
        log.debug("get merchant uid");
        return ResponseDto.ok(paymentService.getMerchantUid());
    }

    @Operation(summary = "결제 검증", description = "iamport 결제 정보를 검증합니다. 검증 요청 후 결제 요청이 필요합니다.")
    @PostMapping("/{imp_uid}/validation")
    public ResponseDto<IamportResponse<Payment>> validateIamport(
        @PathVariable("imp_uid")
        String impUid) {
        log.debug("validate iamport: {}", impUid);
        return ResponseDto.ok(paymentService.validateIamport(impUid));
    }

    @Operation(summary = "결제 처리", description = "결제를 진행합니다.")
    @PostMapping
    public ResponseDto<CommonSuccessDto> processOrder(
        @Auth
        Long memberId,

        @RequestBody
        @Validated
        PaymentRequestDto dto) {
        log.debug("process order: {}", dto);
        return ResponseDto.ok(paymentService.savePay(memberId, dto));
    }
}
