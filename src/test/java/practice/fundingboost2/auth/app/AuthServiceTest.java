package practice.fundingboost2.auth.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import practice.fundingboost2.auth.app.dto.LoginRequestDto;
import practice.fundingboost2.auth.app.dto.RegisterRequestDto;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.common.exception.ErrorCode;
import practice.fundingboost2.config.security.form.CustomAuthenticationProvider;
import practice.fundingboost2.config.security.jwt.JwtTokenGenerator;
import practice.fundingboost2.member.repo.MemberRepository;
import practice.fundingboost2.member.repo.entity.Member;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    AuthService authService;

    @Mock
    MemberRepository memberRepository;

    @Mock
    AuthValidator validator;

    @Mock
    PasswordEncoder encoder;

    @Mock
    CustomAuthenticationProvider provider;

    @Mock
    JwtTokenGenerator tokenGenerator;

    @Mock
    Member member;

    @Test
    void givenValidRequest_whenRegister_thenMemberIsSaved() {
        // given
        RegisterRequestDto dto = new RegisterRequestDto("test@example.com", "testNickname", "password123");
        String encodedPassword = "encodedPassword";
        doReturn(List.of()).when(memberRepository).findByEmailOrNickname(dto.email(), dto.nickname());
        doNothing().when(validator).validateEmailAndNickname(any(), any(), any());
        doReturn(encodedPassword).when(encoder).encode(dto.password());

        // when
        CommonSuccessDto result = authService.register(dto);

        // then
        assertThat(result.isSuccess()).isTrue();
        verify(memberRepository, times(1)).save(any());
    }

    @Test
    void givenExistingEmail_whenRegister_thenThrowsException() {
        // given
        RegisterRequestDto dto = new RegisterRequestDto("existing@example.com", "password123", "existingNickname");
        Member existingMember = new Member("existing@example.com", "anotherNickname", "imageUrl", "phoneNumber");
        doReturn(List.of(existingMember)).when(memberRepository).findByEmailOrNickname(dto.email(), dto.nickname());
        doThrow(new CommonException(ErrorCode.ALREADY_EXISTS_EMAIL)).when(validator)
            .validateEmailAndNickname(any(), eq(dto.email()), any());

        // when & then
        assertThatThrownBy(() -> authService.register(dto))
            .isInstanceOf(CommonException.class);
        verify(memberRepository, never()).save(any());
    }

    @Test
    void givenExistingNickname_whenRegister_thenThrowsException() {
        // given
        RegisterRequestDto dto = new RegisterRequestDto("new@example.com", "password", "existingNickname");
        Member existingMember = new Member("existing@example.com", "existingNickname", "imageUrl", "phoneNumber");
        doReturn(List.of(existingMember)).when(memberRepository).findByEmailOrNickname(dto.email(), dto.nickname());
        doThrow(new CommonException(ErrorCode.ALREADY_EXISTS_EMAIL)).when(validator)
            .validateEmailAndNickname(any(), any(), eq(dto.nickname()));

        // when & then
        assertThatThrownBy(() -> authService.register(dto))
            .isInstanceOf(CommonException.class);
        verify(memberRepository, never()).save(any());
    }

    @Test
    void givenInvalidEmail_whenLogin_thenThrowException() {
        // given
        LoginRequestDto dto = new LoginRequestDto("invalidEmail@naver.com", "password");
        doThrow(new CommonException(ErrorCode.FAILURE_LOGIN)).when(provider).authenticate(any());

        // when & then
        assertThatThrownBy(() -> authService.login(dto))
            .isInstanceOf(CommonException.class);
        verify(tokenGenerator, never()).generateToken(any());
    }

    @Test
    void givenInvalidPassword_whenLogin_thenThrowException() {
        // given
        LoginRequestDto dto = new LoginRequestDto("validEmail@naver.com", "invalidPassword");
        doThrow(new CommonException(ErrorCode.FAILURE_LOGIN)).when(provider).authenticate(any());

        // when & then
        assertThatThrownBy(() -> authService.login(dto))
            .isInstanceOf(CommonException.class);
        verify(tokenGenerator, never()).generateToken(any());
    }
}