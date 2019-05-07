package talent.com.auth.security.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import talent.com.auth.dto.FormLoginDto;

/*
 * 인증 전
 * */
public class PreAuthorizationToken extends UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = -8794700354384940122L;

    private PreAuthorizationToken(String username, String password) {
        super(username, password);
    }

    public PreAuthorizationToken(FormLoginDto dto) {
        this(dto.getId(), dto.getPassword());
    }

    public String getUsername() {
        return (String) super.getPrincipal();
    }

    public String getUserPassword() {
        return (String) super.getCredentials();
    }
}
