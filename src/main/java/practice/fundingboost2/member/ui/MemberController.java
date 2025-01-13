package practice.fundingboost2.member.ui;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.fundingboost2.common.dto.ResponseDto;
import practice.fundingboost2.config.security.annotation.Auth;
import practice.fundingboost2.member.app.MemberService;
import practice.fundingboost2.member.app.dto.GetMemberResponseDto;
import practice.fundingboost2.member.app.dto.UpdateMemberRequestDto;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원 정보 수정", description = "회원 정보를 수정합니다. 변경된 회원 정보를 응답합니다.")
    @PatchMapping
    public ResponseDto<GetMemberResponseDto> updateMember(
        @Auth
        Long id,

        @RequestBody
        UpdateMemberRequestDto requestDto) {
        return ResponseDto.ok(memberService.updateMember(id, requestDto));
    }
}
