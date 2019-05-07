package talent.com.auth.security.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import talent.com.auth.domain.MemberDetail;
import talent.com.auth.domain.MemberRole;
import talent.com.auth.exception.NonAuthenticatedException;
import talent.com.auth.security.token.PostAuthorizationToken;
import talent.com.auth.security.token.PreAuthorizationToken;
import talent.com.auth.service.MemberService;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Component
public class BasicLoginSecurityProvider implements AuthenticationProvider {


    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        PreAuthorizationToken token = (PreAuthorizationToken) authentication;

        String email = token.getUsername();
        String password = token.getUserPassword();

        MemberDetail memberDetail = (MemberDetail) memberService.loadUserByUsername(email);

        if (isCorrectPassword(password, memberDetail)) {
            if (memberDetail.getRole().equals(MemberRole.NON_USER.getRoleName())) {
                throw new NonAuthenticatedException();
            }
            return PostAuthorizationToken.getTokenFromAccountContext(memberDetail);
        }

        throw new NoSuchElementException("인증정보가 정확하지 않습니다");
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthorizationToken.class.isAssignableFrom(authentication);
    }

    private boolean isCorrectPassword(String password, MemberDetail memberDetail) {
        return passwordEncoder.matches(password, memberDetail.getPassword());
    }
}
