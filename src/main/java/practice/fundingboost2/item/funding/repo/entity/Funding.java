package practice.fundingboost2.item.funding.repo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import practice.fundingboost2.common.repo.entity.BaseTimeEntity;
import practice.fundingboost2.member.repo.entity.Member;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Funding extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    private String message;

    @Enumerated(EnumType.STRING)
    private FundingTag tag;

    @Min(0)
    @Column(name = "toatl_price")
    private Integer totalPrice;

    @Min(0)
    @Column(name = "collect_price")
    private Integer collectPrice;

    private LocalDateTime deadLine;

    @Enumerated(EnumType.STRING)
    private FundingStatus status;

    @OneToMany(mappedBy = "funding")
    private final List<FundingItem> fundingItems = new ArrayList<>();

    public Funding(Member member, String message, String tag, LocalDateTime deadLine) {
        this.member = member;
        this.message = message;
        this.tag = FundingTag.valueOf(tag);
        this.deadLine = deadLine;
        this.totalPrice = 0;
        this.collectPrice = 0;
        this.status = FundingStatus.PENDING;
    }
}
