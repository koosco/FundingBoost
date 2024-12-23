package practice.fundingboost2.auth.repo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import practice.fundingboost2.auth.repo.entity.AuthMember;

public interface AuthMemberRepository extends JpaRepository<AuthMember, Long> {

    Optional<AuthMember> findByEmail(String email);

    List<AuthMember> findByEmailOrNickname(String email, String nickname);
}
