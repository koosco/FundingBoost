package practice.fundingboost2.pay.app;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.member.repo.MemberRepository;
import practice.fundingboost2.member.repo.entity.Member;
import practice.fundingboost2.pay.app.dto.PaymentRequestDto;
import practice.fundingboost2.pay.repo.PaymentRepository;

@SpringBootTest
class PaymentServiceTest {

    @Autowired
    PaymentService paymentService;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    MemberRepository memberRepository;

    Member member;

    @BeforeEach
    void init() {
        member = memberRepository.save(new Member("email", "nickname", "imageUrl", "phoneNumber"));
    }

    @Test
    void givenMemberIdAndPayDto_whenSavePay_thenMustSaveCreatedPay() {
        // given
        PaymentRequestDto dto = new PaymentRequestDto(
            "merchantUid",
            "impUid",
            "buyerName",
            "buyerEmail",
            "buyerPhoneNumber",
            "buyerAddress",
            "buyerPostcode",
            1000,
            "productName"
        );

        // when
        CommonSuccessDto responseDto = paymentService.savePay(member.getId(), dto);

        // then
        assertThat(responseDto.isSuccess()).isTrue();
    }
    
    @Test
    void givenMember_whenSavePay_thenMemberPointMustBeIncrease() {
        // given
        PaymentRequestDto dto = new PaymentRequestDto(
            "merchantUid",
            "impUid",
            "buyerName",
            "buyerEmail",
            "buyerPhoneNumber",
            "buyerAddress",
            "buyerPostcode",
            1000,
            "productName"
        );
        // when
        paymentService.savePay(member.getId(), dto);
        Member savedMember = memberRepository.findById(member.getId())
            .orElseThrow();

        // then
        assertThat(savedMember.getPoint()).isEqualTo(1000);
    }
}