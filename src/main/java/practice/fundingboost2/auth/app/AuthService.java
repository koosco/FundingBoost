package practice.fundingboost2.auth.app;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import practice.fundingboost2.auth.app.dto.LoginRequestDto;
import practice.fundingboost2.auth.app.dto.LoginResponseDto;
import practice.fundingboost2.auth.app.dto.RegisterRequestDto;
import practice.fundingboost2.auth.dao.AuthMemberRepository;
import practice.fundingboost2.auth.dao.entity.AuthMember;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.config.security.form.CustomAuthenticationProvider;
import practice.fundingboost2.config.security.jwt.JwtTokenGenerator;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthMemberRepository memberRepository;
    private final AuthValidator validator;
    private final PasswordEncoder encoder;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final JwtTokenGenerator tokenGenerator;


    public CommonSuccessDto register(RegisterRequestDto dto) {
        List<AuthMember> findMembers = memberRepository.findByEmailOrNickname(dto.email(), dto.nickname());
        validate(dto, findMembers);

        String encodedPassword = encode(dto.password());
        AuthMember member = AuthMember.create(dto.email(), dto.nickname(), encodedPassword);
        memberRepository.save(member);

        return CommonSuccessDto.fromEntity(true);
    }

    public LoginResponseDto login(LoginRequestDto dto) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        Authentication token = customAuthenticationProvider.authenticate(authentication);
        String accessToken = tokenGenerator.generateToken(token);

        return new LoginResponseDto(accessToken);
    }

    private void validate(RegisterRequestDto dto, List<AuthMember> findMembers) {
        validator.validateEmailAndNickname(findMembers, dto.email(), dto.nickname());
    }

    private String encode(String password) {
        return encoder.encode(password);
    }
}
