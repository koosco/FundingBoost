package practice.fundingboost2.item.order.repo.entity;

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
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.common.exception.ErrorCode;
import practice.fundingboost2.common.repo.entity.BaseTimeEntity;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.member.repo.entity.Member;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor
public class Order extends BaseTimeEntity {

    private static final Integer DEFAULT_QUANTITY = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Item item;

    private String optionName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Delivery delivery;

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus = OrderStatus.PENDING_PAYMENT;

    @Column(nullable = false)
    private boolean reviewWritten;

    public Order(Member member, Item item, String optionName, Delivery delivery, Integer quantity) {
        this.member = member;
        this.item = item;
        this.optionName = optionName;
        this.delivery = delivery;
        this.quantity = quantity;
        this.reviewWritten = false;
    }

    public Order(Member member, Item item , String optionName, Delivery delivery) {
        this(member, item , optionName, delivery, DEFAULT_QUANTITY);
    }

    public void writeReview() {
        reviewWritten = true;
    }

    public void validateReviewWritten(Long memberId) {
        validateMember(memberId);
        validateReviewWritten();
    }

    private void validateMember(Long memberId) {
        if (member.validateId(memberId)) {
            throw new CommonException(ErrorCode.NOT_FOUND_PURCHASED_ORDER);
        }
    }

    private void validateReviewWritten() {
        if (reviewWritten) {
            throw new CommonException(ErrorCode.ALREADY_WRITTEN_REVIEW);
        }
    }
}
