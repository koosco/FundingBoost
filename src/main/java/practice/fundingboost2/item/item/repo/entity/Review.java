package practice.fundingboost2.item.item.repo.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import practice.fundingboost2.common.repo.entity.BaseTimeEntity;
import practice.fundingboost2.member.repo.entity.Member;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Range(min = 1, max = 5)
    private int score;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Option itemOption;

    @Size(max = 3)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "review_image",
        joinColumns = @JoinColumn(name = "review_id"),
        foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private List<String> reviewImages;

    public Review(int score, String content, Member member, Item item, Option itemOption) {
        this(score, content, member, item, itemOption, List.of());
    }

    public Review(int score, String content, Member member, Item item, Option itemOption, List<String> reviewImages) {
        this.score = score;
        this.content = content;
        this.member = member;
        this.item = item;
        this.itemOption = itemOption;
        this.reviewImages = reviewImages;
        item.review();
    }
}
