package practice.fundingboost2.member.repo.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.common.exception.ErrorCode;

@Getter
@Entity
@ToString
@Table(name = "member")
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String email;

    @NotBlank
    private String nickname;

    private String imageUrl;

    @Embedded
    private Point point;

    private String phoneNumber;

    public Member(String email, String nickname, String imageUrl, String phoneNumber) {
        validate(email, nickname);
        this.email = email;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.phoneNumber = phoneNumber;
        this.point = Point.ZERO;
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
