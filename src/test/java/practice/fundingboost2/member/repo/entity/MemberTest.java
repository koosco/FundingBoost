package practice.fundingboost2.member.repo.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import practice.fundingboost2.common.exception.CommonException;

@SpringBootTest
class MemberTest {

    Member member;

    @BeforeEach
    void init() {
        member = new Member("email", "nickname", "imageUrl", "phoneNumber");
    }

    @Test
    void givenEmailAndNickname_whenCreateMember_thenCreateNewMember() {
        // given
        // when
        // then
        assertDoesNotThrow(() -> new Member("email", "nickname", null, null));
    }

    @Test
    void givenEmailNull_whenCreateMember_thenThrowException() {
        // given
        // when
        // then
        assertThrows(CommonException.class, () -> new Member(null, "nickname", null, null));
    }

    @Test
    void givenNicknameNull_whenCreateMember_thenThrowException() {
        // given
        // when
        // then
        assertThrows(CommonException.class, () -> new Member("email", null, null, null));
    }

    @Test
    void givenImageUrlAndPhoneNumberNull_whenCreateMember_thenDoesNotThrowException() {
        // given
        // when
        // then
        assertDoesNotThrow(() -> new Member("email", "nickname", null, null));
    }
}