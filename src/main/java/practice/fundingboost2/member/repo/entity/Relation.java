package practice.fundingboost2.member.repo.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.common.exception.ErrorCode;

@Getter
@Entity
@ToString
@NoArgsConstructor
public class Relation {

    @EmbeddedId
    private RelationId id;

    public Relation(Long member1Id, Long member2Id) {
        validate(member1Id, member2Id);
        if (member1Id > member2Id) {
            Long tmp = member1Id;
            member1Id = member2Id;
            member2Id = tmp;
        }
        this.id = new RelationId(member1Id, member2Id);
    }

    private static void validate(Long member1Id, Long member2Id) {
        if (member1Id.equals(member2Id)) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }
    }
}
