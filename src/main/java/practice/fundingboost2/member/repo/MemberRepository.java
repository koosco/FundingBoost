package practice.fundingboost2.member.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.fundingboost2.member.repo.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
