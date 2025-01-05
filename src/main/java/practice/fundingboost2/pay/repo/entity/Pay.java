package practice.fundingboost2.pay.repo.entity;

import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import practice.fundingboost2.member.repo.entity.Member;

@Getter
@Entity
@NoArgsConstructor
public class Pay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String merchantUid;

    private String impUid;

    private String buyerName;

    private String buyerEmail;

    private String buyerPhoneNumber;

    private String buyerAddress;

    private String buyerPostcode;

    private Integer amount;

    private String productName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    public Pay(Member member, String merchantUid, String impUid, String buyerName, String buyerEmail, String buyerPhoneNumber,
        String buyerAddress, String buyerPostcode, Integer amount, String productName) {
        this.member = member;
        this.merchantUid = merchantUid;
        this.impUid = impUid;
        this.buyerName = buyerName;
        this.buyerEmail = buyerEmail;
        this.buyerPhoneNumber = buyerPhoneNumber;
        this.buyerAddress = buyerAddress;
        this.buyerPostcode = buyerPostcode;
        this.amount = amount;
        this.productName = productName;
    }
}
