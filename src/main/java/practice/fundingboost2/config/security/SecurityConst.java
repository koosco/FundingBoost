package practice.fundingboost2.config.security;

import java.util.List;

public class SecurityConst {

  private SecurityConst() {}
  public static final List<String> ALLOWED_URLS =
      List.of("/api/**");
}
