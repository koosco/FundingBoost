package practice.fundingboost2.auth.repo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.common.exception.ErrorCode;

@Getter
@Entity
@Table(name = "member")
public class AuthMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickname;

    private String password;

    public static AuthMember create(String email, String nickname, String password) {
        AuthMember authMember = new AuthMember();
        authMember.email = email;
        authMember.nickname = nickname;
        authMember.password = password;

        return authMember;
    }

    public void validateEmail(String email) {
        if (this.email.equals(email)) {
            throw new CommonException(ErrorCode.ALREADY_EXISTS_EMAIL);
        }
    }

    public void validateNickname(String nickname) {
        if (this.email.equals(nickname)) {
            throw new CommonException(ErrorCode.ALREADY_EXISTS_EMAIL);
        }
    }
}
