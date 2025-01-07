package practice.fundingboost2.auth.ui;


import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.fundingboost2.auth.app.AuthService;
import practice.fundingboost2.auth.app.dto.LoginRequestDto;
import practice.fundingboost2.auth.app.dto.RegisterRequestDto;
import practice.fundingboost2.auth.app.dto.TokenDto;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.common.dto.ResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseDto<TokenDto> login(
        @RequestBody
        @Validated
        LoginRequestDto dto) {
        return ResponseDto.ok(authService.login(dto));
    }

    @PostMapping("/register")
    public ResponseDto<CommonSuccessDto> register(
        @RequestBody
        @Validated
        RegisterRequestDto dto) {
        return ResponseDto.created(authService.register(dto));
    }
}
