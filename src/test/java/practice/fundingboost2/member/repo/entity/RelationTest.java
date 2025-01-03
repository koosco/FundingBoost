package practice.fundingboost2.member.repo.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import practice.fundingboost2.common.exception.CommonException;

class RelationTest {

    @Test
    void givenMemberIdAndFriendId_thenCreateNewRelation() {
        // given
        Long memberId = 1L;
        Long friendId = 2L;

        // when
        Relation relation = new Relation(memberId, friendId);
        // then
        assertThat(relation.getId()).isEqualTo(new RelationId(memberId, friendId));
    }

    @Test
    void givenMemberIdAndFriendIdEquals_whenCreateRelation_thenThrowException() {
        // given
        Long memberId = 1L;
        Long friendId = 1L;

        // when
        // then
        assertThatThrownBy(() -> new Relation(memberId, friendId))
            .isInstanceOf(CommonException.class);
    }


}