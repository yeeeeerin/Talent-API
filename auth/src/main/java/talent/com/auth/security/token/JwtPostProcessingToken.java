package talent.com.auth.security.token;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import talent.com.auth.domain.MemberRole;

import java.util.Collection;

public class JwtPostProcessingToken extends UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = -5711844140485338527L;

    private JwtPostProcessingToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public JwtPostProcessingToken(String username, MemberRole role) {
        super(username, 1234, MemberRole.parseAuthorities(role));
    }


    public String getUserId() {
        return (String) getPrincipal();
    }

    public String getPassword() {
        return (String) getCredentials();
    }

}