package practice.fundingboost2.item.order.repo.entity;

import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.common.exception.ErrorCode;
import practice.fundingboost2.member.repo.entity.Member;

@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String userName;

    @NotBlank
    private String address;

    @NotBlank
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    public Delivery(String userName, String address, String phoneNumber, Member member) {
        this.userName = userName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.member = member;
    }

    public void validateMember(Member member) {
        if (!this.member.equals(member)) {
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }
    }

    public void update(String username, String address, String phoneNumber) {
        if (username != null) {
            this.userName = username;
        }
        if (address != null) {
            this.address = address;
        }
        if (phoneNumber != null) {
            this.phoneNumber = phoneNumber;
        }
    }
}
