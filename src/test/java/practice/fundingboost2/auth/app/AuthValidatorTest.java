package practice.fundingboost2.auth.app;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.member.repo.entity.Member;

@SpringBootTest
class AuthValidatorTest {

    List<Member> members = new ArrayList<>();

    final int MEMBER_SIZE = 5;

    @Autowired
    AuthValidator authValidator;

    @BeforeEach
    void init() {
        IntStream.range(0, MEMBER_SIZE)
            .forEach(
                i -> members.add(new Member("email" + i, "nickname" + i, "imageUrl" + i, "phoneNumber" + i))
            );
    }
    
    @Test
    void givenMembers_whenEmailAndNicknameDoesNotExists_thenMustNotThrowException() {
        // given
        // when
        // then
        assertThatNoException()
            .isThrownBy(() -> authValidator.validateEmailAndNickname(members, "newEmail", "newNickname"));
    }

    @Test
    void givenMembers_whenEmailDuplicated_thenThrowException() {
        // given
        // when
        // then
        assertThatThrownBy(() -> authValidator.validateEmailAndNickname(members, members.getFirst().getEmail(), "newNickname"))
            .isInstanceOf(CommonException.class);
    }

    @Test
    void givenMembers_whenNicknameDuplicated_thenThrowException() {
        // given
        // when
        // then
        assertThatThrownBy(() -> authValidator.validateEmailAndNickname(members, "newEmail", members.getFirst().getNickname()))
            .isInstanceOf(CommonException.class);
    }
}