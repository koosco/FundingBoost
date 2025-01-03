package practice.fundingboost2.member.repo;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import practice.fundingboost2.member.repo.entity.Member;

@DataJpaTest
class MemberRepositoryTest {

    Member member;

    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    static final String NOT_EXISTS_EMAIL = "notExistsEmail";
    static final String NOT_EXISTS_NICKNAME = "notExistsNickname";

    @BeforeEach
    void init() {
        member = new Member("email", "nickname", "imageUrl", "phoneNumber");
        em.persist(member);
        em.flush();
    }

    @Test
    void givenMember_whenFindByEmail_thenReturnFindMember() {
        // given
        // when
        Optional<Member> findMember = memberRepository.findByEmail(member.getEmail());

        // then
        assertThat(findMember).isNotEmpty();
        assertThat(findMember.get().getEmail()).isEqualTo(member.getEmail());
        assertThat(findMember.get().getNickname()).isEqualTo(member.getNickname());
        assertThat(findMember.get().getImageUrl()).isEqualTo(member.getImageUrl());
        assertThat(findMember.get().getPhoneNumber()).isEqualTo(member.getPhoneNumber());
    }
    
    @Test
    void givenNotExistsEmailMember_whenFindByEmail_thenReturnEmptyOptional() {
        // given
        String NOT_EXISTS_EMAIL = "notExistsEmail";
        // when
        Optional<Member> findMember = memberRepository.findByEmail(NOT_EXISTS_EMAIL);
        // then
        assertThat(findMember).isEmpty();
    }

    @Test
    void givenEmailAndNickname_whenFindByEmailOrNickname_thenReturnMemberList() {
        // given
        Member member2 = new Member("email", "nickname2", "imageUrl", "phoneNumber");
        em.persist(member2);
        em.flush();

        // when
        List<Member> members = memberRepository.findByEmailOrNickname(member.getEmail(),
            member2.getNickname());

        // then
        assertThat(members).hasSize(2);
    }

    @Test
    void givenNoMembers_whenFindByEmailOrNickname_thenReturnEmptyList() {
        // given
        // when
        List<Member> members = memberRepository.findByEmailOrNickname(NOT_EXISTS_EMAIL, NOT_EXISTS_NICKNAME);
        // then
        assertThat(members).isEmpty();
    }

    @Test
    void givenExistEmail_whenFindByEmailOrNickname_thenReturnFoundMember() {
        // given
        // when
        List<Member> members = memberRepository.findByEmailOrNickname(member.getEmail(), NOT_EXISTS_NICKNAME);
        // then
        assertThat(members).hasSize(1);
        assertThat(members.getFirst().getEmail()).isEqualTo(member.getEmail());
    }

    @Test
    void givenExistNickname_whenFindByEmailOrNickname_thenReturnFoundMember() {
        // given
        // when
        List<Member> members = memberRepository.findByEmailOrNickname(NOT_EXISTS_EMAIL, member.getNickname());
        // then
        assertThat(members).hasSize(1);
        assertThat(members.getFirst().getNickname()).isEqualTo(member.getNickname());
    }
}