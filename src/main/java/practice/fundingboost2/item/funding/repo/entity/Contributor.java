package practice.fundingboost2.item.funding.repo.entity;

import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import practice.fundingboost2.member.repo.entity.Member;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Contributor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int contribution;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funding_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Funding funding;

    public Contributor(Member member, Funding funding, int contribution) {
        this.member = member;
        this.funding = funding;
        this.contribution = contribution;
        funding.fund(contribution);
    }
}
