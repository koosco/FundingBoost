package practice.fundingboost2.item.order.repo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Embedded;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import practice.fundingboost2.common.repo.entity.Price;
import practice.fundingboost2.item.gifthub.repo.entity.Quantity;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.member.repo.entity.Member;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Item item;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Delivery delivery;

    @Embedded
    @Column(nullable = false)
    private Price totalPrice = new Price();

    @Embedded
    @Column(nullable = false)
    private Quantity quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus = OrderStatus.PENDING_PAYMENT;

    public Order(Member member, Item item, Delivery delivery, Quantity quantity) {
        this.member = member;
        this.item = item;
        this.delivery = delivery;
        this.totalPrice = totalPrice.add(item.getPriceEntity());
        this.quantity = quantity;
    }

    public Order(Member member, Item item, Delivery delivery) {
        this(member, item, delivery, new Quantity(1));
    }
}