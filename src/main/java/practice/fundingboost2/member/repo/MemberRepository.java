package practice.fundingboost2.member.repo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import practice.fundingboost2.member.repo.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    List<Member> findByEmailOrNickname(String email, String nickname);
}
