package talent.com.auth.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import talent.com.auth.domain.MemberDetail;
import talent.com.auth.security.token.PostAuthorizationToken;
import talent.com.auth.security.token.PreAuthorizationToken;
import talent.com.auth.security.utils.JwtDecoder;

/*
 * jwt인증 전 토큰을 다룸
 * */
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) {

        String token = (String) authentication.getPrincipal();
        MemberDetail detail = JwtDecoder.decodeJwt(token);

        return PostAuthorizationToken.getTokenFromAccountContext(detail);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthorizationToken.class.isAssignableFrom(authentication);
    }
}
