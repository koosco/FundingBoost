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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.common.exception.ErrorCode;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.repo.entity.Option;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FundingItem {

    private static final int MAX_SEQUENCE = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funding_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Funding funding;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Option option;

    @Column(columnDefinition = "INTEGER CHECK (sequence BETWEEN 1 AND 5)")
    private Integer sequence;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FundingItemStatus status;

    public FundingItem(Funding funding, Item item, Option option, Integer sequence) {
        validateSequence(sequence);
        this.funding = funding;
        this.item = item;
        this.option = option;
        this.sequence = sequence;
        this.status = FundingItemStatus.PENDING;
        item.fund();
        funding.getFundingItems().add(this);
        funding.plusTotalPrice(item.getPrice());
    }

    private void validateSequence(Integer sequence) {
        if (sequence > MAX_SEQUENCE) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }
    }
}
