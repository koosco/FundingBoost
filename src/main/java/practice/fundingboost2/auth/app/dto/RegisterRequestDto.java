package practice.fundingboost2.auth.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(
    @Email
    @NotBlank
    String email,

    @Size(min = 8)
    @NotBlank
    String password,

    @NotBlank
    String nickname) {

}
