package practice.fundingboost2.auth.app;

import java.util.List;
import org.springframework.stereotype.Component;
import practice.fundingboost2.auth.dao.entity.AuthMember;

@Component
public class AuthValidator {

    public void validateEmailAndNickname(List<AuthMember> members, String email, String nickname) {
        validateEmail(members, email);
        validateNickname(members, nickname);
    }

    private static void validateNickname(List<AuthMember> members, String nickname) {
        members.forEach(member -> member.validateNickname(nickname));
    }

    private static void validateEmail(List<AuthMember> members, String email) {
        members.forEach(member -> member.validateEmail(email));
    }
}
