package practice.fundingboost2.member.repo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.common.exception.ErrorCode;

@Getter
@Entity
@ToString
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    @NotBlank
    private String email;

    @NotBlank
    private String nickname;

    @Column(length = 500)
    private String imageUrl;

    @Min(0)
    @NotNull
    private Integer point;

    private String phoneNumber;

    public Member(String email, String nickname, String imageUrl, String phoneNumber) {
        validate(email, nickname);
        this.email = email;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.phoneNumber = phoneNumber;
        this.point = 0;
    }

    public static Member create(String email, String nickname, String password) {
        Member member = new Member();
        member.email = email;
        member.nickname = nickname;
        member.password = password;
        member.point = 0;

        return member;
    }

    public boolean validateId(Long memberId) {
        return this.id.equals(memberId);
    }

    public void increasePoint(Integer point) {
        this.point += point;
    }

    public void decreasePoint(Integer point) {
        validatePoint(point);
        this.point -= point;
    }

    private void validatePoint(Integer point) {
        if (this.point < point) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }
    }

    public void validateEmail(String email) {
        if (this.email.equals(email)) {
            throw new CommonException(ErrorCode.ALREADY_EXISTS_EMAIL);
        }
    }

    public void validateNickname(String nickname) {
        if (this.nickname.equals(nickname)) {
            throw new CommonException(ErrorCode.ALREADY_EXISTS_EMAIL);
        }
    }

    private static void validate(String email, String nickname) {
        if (email == null) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }
        if (nickname == null) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }
    }

    public void update(String nickname, String imageUrl, String phoneNumber) {
        if (nickname != null) {
            this.nickname = nickname;
        }
        if (imageUrl != null) {
            this.imageUrl = imageUrl;
        }
        if (phoneNumber != null) {
            this.phoneNumber = phoneNumber;
        }
    }
}
