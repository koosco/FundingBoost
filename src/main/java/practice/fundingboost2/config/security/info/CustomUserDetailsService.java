package practice.fundingboost2.config.security.info;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.common.exception.ErrorCode;
import practice.fundingboost2.member.repo.MemberRepository;
import practice.fundingboost2.member.repo.entity.Member;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member entity = memberRepository.findByEmail(email)
            .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_LOGIN_USER));
        return new CustomUserDetails(entity);
    }
}
