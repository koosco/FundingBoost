package practice.fundingboost2.member.ui;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.fundingboost2.common.dto.ResponseDto;
import practice.fundingboost2.config.security.annotation.Auth;
import practice.fundingboost2.member.app.MemberService;
import practice.fundingboost2.member.app.dto.GetMemberResponseDto;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberQueryController {

    private final MemberService memberService;

    @Operation(summary = "회원 정보 조회", description = "회원 정보를 조회합니다.")
    @GetMapping
    public ResponseDto<GetMemberResponseDto> getMember(
        @Auth
        Long memberId) {
        return ResponseDto.ok(memberService.getMember(memberId));
    }
}
