package practice.fundingboost2.config.security.jwt;

import static org.springframework.util.StringUtils.hasText;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtProcessingFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    public JwtProcessingFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        String token = getToken(request);
        setAuthentication(token);
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String token) {
        if (hasText(token)) {
            UsernamePasswordAuthenticationToken unAuthorization = new UsernamePasswordAuthenticationToken(
                token, "");
            Authentication authentication = jwtProvider.authenticate(unAuthorization);
            SecurityContextHolder.getContextHolderStrategy().getContext().setAuthentication(authentication);
        }
    }

    private static String getToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
}
