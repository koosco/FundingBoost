package practice.fundingboost2.pay.ui;

import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import practice.fundingboost2.pay.app.dto.PayDto;

@Slf4j
@RestController
@RequestMapping("/api/pay")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseDto<String> getMerchantUid() {
        log.debug("get merchant uid");
        return ResponseDto.ok(paymentService.getMerchantUid());
    }

    @PostMapping("/{imp_uid}/validation")
    public ResponseDto<IamportResponse<Payment>> validateIamport(@PathVariable("imp_uid") String impUid) {
        log.debug("validate iamport: {}", impUid);
        return ResponseDto.ok(paymentService.validateIamport(impUid));
    }

    @PostMapping
    public ResponseDto<CommonSuccessDto> processOrder(@Auth Long memberId, @RequestBody PayDto dto) {
        log.debug("process order: {}", dto);
        return ResponseDto.ok(paymentService.savePay(memberId, dto));
    }
}
