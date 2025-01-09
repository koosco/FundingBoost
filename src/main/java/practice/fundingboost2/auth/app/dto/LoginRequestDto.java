package practice.fundingboost2.auth.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "로그인 요청 Dto")
public record LoginRequestDto(

    @Schema(
        description = "이메일",
        example = "rnxogud123@naver.com"
    )
    @Email
    @NotBlank
    String email,

    @Schema(
        description = "비밀번호. 8자 이상",
        example = "password1234"
    )
    @Size(min = 8)
    @NotBlank
    String password) {

}
