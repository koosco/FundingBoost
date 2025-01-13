package practice.fundingboost2.auth.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "access token")
public record TokenDto(

    @Schema(
        description = "access token",
        example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiaWF0IjoxNTE2MjM5MDIyfQ")
    String accessToken) {

}
