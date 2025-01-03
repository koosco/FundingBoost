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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.common.exception.ErrorCode;
import practice.fundingboost2.common.repo.entity.BaseTimeEntity;
import practice.fundingboost2.member.repo.entity.Member;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Funding extends BaseTimeEntity {

    private static final int MAXIMUM_EXTEND_DAYS = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    private String message;

    @Enumerated(EnumType.STRING)
    private FundingTag tag;

    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    @Column(name = "collect_price", nullable = false)
    private Integer collectPrice;

    @Column(nullable = false)
    private LocalDateTime deadLine;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FundingStatus status;

    @Column(nullable = false)
    private Integer fundingCount;

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
        this.fundingCount = 0;
    }

    public void fund(int fundMoney) {
        validateFundingPrice();
        isTerminated();
        this.fundingCount++;
        collectPrice += fundMoney;
    }

    private void validateFundingPrice() {
        if (totalPrice > 0 && collectPrice >= totalPrice) {
            throw new CommonException(ErrorCode.INVALID_FUNDING_STATUS);
        }
    }

    public void plusTotalPrice(int money) {
        totalPrice += money;
    }

    public void validateMember(Member member) {
        if (!this.member.equals(member)) {
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }
    }

    public void update(LocalDateTime deadline, String fundingStatus) {
        isTerminated();

        if (deadline != null) {
            validateDeadline(deadline);
            this.deadLine = deadline;
        }

        if (fundingStatus != null) {
            this.status = FundingStatus.valueOf(fundingStatus);
        }
    }

    private void validateDeadline(LocalDateTime deadline) {
        isDeadlineEarlierThanNow(deadline);
        idDeadlineOverMaxExtendDays(deadline);
    }

    private void isTerminated() {
        isDeadlineExceed();
        isFundingStatusTerminated();
    }

    private void idDeadlineOverMaxExtendDays(LocalDateTime deadline) {
        if (deadline.isAfter(this.deadLine.plusDays(MAXIMUM_EXTEND_DAYS))) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }
    }

    private static void isDeadlineEarlierThanNow(LocalDateTime deadline) {
        if (deadline.isBefore(LocalDateTime.now())) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }
    }

    private void isDeadlineExceed() {
        if (this.deadLine.isBefore(LocalDateTime.now())) {
            throw new CommonException(ErrorCode.INVALID_FUNDING_STATUS);
        }
    }

    private void isFundingStatusTerminated() {
        if (this.status.equals(FundingStatus.FAILED) || this.status.equals(FundingStatus.SUCCESS)) {
            throw new CommonException(ErrorCode.INVALID_FUNDING_STATUS);
        }
    }
}
