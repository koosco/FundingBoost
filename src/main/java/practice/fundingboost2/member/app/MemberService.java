package practice.fundingboost2.member.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.common.exception.ErrorCode;
import practice.fundingboost2.member.repo.MemberRepository;
import practice.fundingboost2.member.app.dto.GetMemberResponseDto;
import practice.fundingboost2.member.repo.entity.Member;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member findMember(Long id) {
        return memberRepository.findById(id)
            .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_LOGIN_USER));
    }

    public GetMemberResponseDto getMember(Long id) {
        Member member = findMember(id);
        return GetMemberResponseDto.from(member);
    }
}
