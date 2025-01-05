package practice.fundingboost2.pay.app;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.common.exception.ErrorCode;
import practice.fundingboost2.member.app.MemberService;
import practice.fundingboost2.member.repo.entity.Member;
import practice.fundingboost2.pay.app.dto.PayDto;
import practice.fundingboost2.pay.repo.PayRepository;
import practice.fundingboost2.pay.repo.entity.Pay;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final IamportClient iamportClient;
    private final MerchantUidGenerator generator;
    private final PayRepository payRepository;
    private final MemberService memberService;

    public String getMerchantUid() {
        return generator.generate();
    }

    public IamportResponse<Payment> validateIamport(String impUid) {
        try {
            IamportResponse<Payment> payment = iamportClient.paymentByImpUid(impUid);
            Payment response = payment.getResponse();
            log.info("{} : Iamport Response, buyer name: {}, fail reason: {}, started at: {}, paid at: {}",
                impUid,
                response.getBuyerName(),
                response.getFailReason(),
                response.getStartedAt(),
                response.getPaidAt());
            if (payment.getResponse() == null) {
                log.info("{} : Iamport API Failed. Payment Response is null.", impUid);
                throw new CommonException(ErrorCode.PAYMENT_INVALID_ERROR);
            }
            return payment;
        } catch (IamportResponseException e) {
            log.error("{} : Iamport API error: {}, code: {}",
                impUid,
                e.getMessage(),
                e.getHttpStatusCode(),
                e);
            throw new CommonException(ErrorCode.PAYMENT_VALIDATION_ERROR);
        } catch (IOException e) {
            log.error("네트워크 오류 발생: {}", e.getMessage(), e);
            throw new CommonException(ErrorCode.PAYMENT_NETWORK_ERROR);
        }
    }

    @Transactional
    public CommonSuccessDto saveOrder(Long memberId, PayDto dto) {
        Member member = memberService.findMember(memberId);
        Pay pay = new Pay(member,
            dto.merchantUid(),
            dto.impUid(),
            dto.buyerName(),
            dto.buyerEmail(),
            dto.phoneNumber(),
            dto.address(),
            dto.postcode(),
            dto.amount(),
            dto.productName());
        payRepository.save(pay);
        log.info("{} save success", pay.getMerchantUid());
        return CommonSuccessDto.fromEntity(true);
    }
}
