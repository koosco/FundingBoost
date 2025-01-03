package practice.fundingboost2.member.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.member.app.dto.GetMemberResponseDto;
import practice.fundingboost2.member.app.dto.UpdateMemberRequestDto;
import practice.fundingboost2.member.repo.MemberRepository;
import practice.fundingboost2.member.repo.entity.Member;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    Member member;

    @BeforeEach
    void init() {
        member = new Member("email", "nickname", "imageUrl", "phoneNumber");
        member = memberRepository.save(member);
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
        assertThatThrownBy(() -> memberService.getMember(NOT_EXISTS_ID))
            .isInstanceOf(CommonException.class);
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
    
    @Test
    void givenMember_whenMemberExists_thenReturnTrue() {
        // given
        // when
        Boolean result = memberService.existsById(member.getId());
        // then
        assertThat(result).isTrue();
    }

    @Test
    void whenMemberNotExists_thenReturnFalse() {
        // given
        // when
        Boolean result = memberService.existsById(100_000_000_000L);
        // then
        assertThat(result).isFalse();
    }
}