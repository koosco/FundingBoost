package practice.fundingboost2.member.ui;

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

    @PatchMapping
    public ResponseDto<GetMemberResponseDto> updateMember(
        @Auth
        Long id,

        @RequestBody
        UpdateMemberRequestDto requestDto) {
        return ResponseDto.ok(memberService.updateMember(id, requestDto));
    }
}
