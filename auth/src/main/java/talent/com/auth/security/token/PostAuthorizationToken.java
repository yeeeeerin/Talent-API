package talent.com.auth.security.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import talent.com.auth.domain.MemberDetail;

import java.util.Collection;

/*
 * - 유저 모델을 그냥 사용
 * - 유저디테일즈 구현체를 사용(스프링시큐리티에서는 이 방식을 기본으로 사용)
 * */
public class PostAuthorizationToken extends UsernamePasswordAuthenticationToken {


    private static final long serialVersionUID = 109270960034675374L;

    private PostAuthorizationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public static PostAuthorizationToken getTokenFromAccountContext(MemberDetail memberDetail) {
        return new PostAuthorizationToken(memberDetail, memberDetail.getPassword(), memberDetail.getAuthorities());
    }

    public MemberDetail getMemberContext() {
        return (MemberDetail) super.getPrincipal();
    }

}
