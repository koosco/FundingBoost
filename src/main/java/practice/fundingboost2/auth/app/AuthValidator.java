package practice.fundingboost2.auth.app;

import java.util.List;
import org.springframework.stereotype.Component;
import practice.fundingboost2.member.repo.entity.Member;

@Component
public class AuthValidator {

    public void validateEmailAndNickname(List<Member> members, String email, String nickname) {
        validateEmail(members, email);
        validateNickname(members, nickname);
    }

    private static void validateNickname(List<Member> members, String nickname) {
        members.forEach(member -> member.validateNickname(nickname));
    }

    private static void validateEmail(List<Member> members, String email) {
        members.forEach(member -> member.validateEmail(email));
    }
}
