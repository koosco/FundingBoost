package practice.fundingboost2.member.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.member.app.dto.GetMemberResponseDto;
import practice.fundingboost2.member.app.dto.UpdateMemberRequestDto;
import practice.fundingboost2.member.repo.entity.Member;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @PersistenceContext
    EntityManager em;

    Member member;

    @BeforeEach
    void init() {
        member = new Member("email", "nickname", "imageUrl", "phoneNumber");
        em.persist(member);
        em.flush();
    }

    @Test
    void getMember() {
        // given
        // when
        GetMemberResponseDto dto = memberService.getMember(member.getId());

        // then
        assertThat(dto.email()).isEqualTo(member.getEmail());
        assertThat(dto.nickname()).isEqualTo(member.getNickname());
        assertThat(dto.imageUrl()).isEqualTo(member.getImageUrl());
        assertThat(dto.phoneNumber()).isEqualTo(member.getPhoneNumber());
    }

    @Test
    void givenCreatedMember_whenNotFound_thenThrowException() {
        // given
        Long NOT_EXISTS_ID = 100_000_000_000L;
        // when
        // then
        assertThrows(CommonException.class, () -> memberService.getMember(NOT_EXISTS_ID));
    }

    @Test
    void givenCreatedMember_whenNewNickname_thenUpdateNickname() {
        // given
        UpdateMemberRequestDto dto = new UpdateMemberRequestDto("newNickname", null);
        // when
        GetMemberResponseDto updateDto = memberService.updateMember(member.getId(), dto);
        // then
        assertThat(updateDto.nickname()).isEqualTo("newNickname");
    }

    @Test
    void givenCreatedMember_whenNewPhoneNumber_thenUpdatePhoneNumber() {
        // given
        UpdateMemberRequestDto dto = new UpdateMemberRequestDto(null, "newPhoneNumber");
        // when
        GetMemberResponseDto updateDto = memberService.updateMember(member.getId(), dto);
        // then
        assertThat(updateDto.phoneNumber()).isEqualTo("newPhoneNumber");
    }
}