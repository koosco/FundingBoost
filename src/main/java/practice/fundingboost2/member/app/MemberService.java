package practice.fundingboost2.member.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.common.exception.ErrorCode;
import practice.fundingboost2.member.app.dto.GetMemberResponseDto;
import practice.fundingboost2.member.app.dto.UpdateMemberRequestDto;
import practice.fundingboost2.member.repo.MemberRepository;
import practice.fundingboost2.member.repo.entity.Member;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Member findMember(Long id) {
        return memberRepository.findById(id)
            .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_LOGIN_USER));
    }

    @Transactional
    public Boolean existsById(Long id) {
        return memberRepository.existsById(id);
    }

    public GetMemberResponseDto getMember(Long id) {
        Member member = findMember(id);
        return GetMemberResponseDto.from(member);
    }

    public GetMemberResponseDto updateMember(Long id, UpdateMemberRequestDto dto) {
        Member member = findMember(id);
        member.update(dto.nickname(), null, dto.phoneNumber());

        return GetMemberResponseDto.from(member);
    }
}
