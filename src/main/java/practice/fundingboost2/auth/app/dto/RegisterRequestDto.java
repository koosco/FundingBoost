package practice.fundingboost2.auth.app.dto;

public record RegisterRequestDto(
    String email,

    String password,

    String nickname) {

}
