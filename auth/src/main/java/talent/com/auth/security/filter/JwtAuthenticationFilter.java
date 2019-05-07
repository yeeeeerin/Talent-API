package talent.com.auth.security.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import talent.com.auth.security.token.JwtPreProcessingToken;
import talent.com.auth.security.utils.HeaderTokenExtractor;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    /*
     * 사용자가 올바른 토큰을 들고있지 않을 때 exception을 예외맵핑을 해주는 jwt인증용 실패handler와
     * 토큰 값을 빼오는 컴포넌트가 필요함
     * */

    private HeaderTokenExtractor extractor;

    /*
     * RequestMatcher를 직접 구현해서 사용
     * */
    private JwtAuthenticationFilter(final RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    public JwtAuthenticationFilter(final RequestMatcher matcher, final HeaderTokenExtractor extractor) {
        super(matcher);
        this.extractor = extractor;
    }

    /*
     *
     * */
    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        final String prePayload = request.getHeader("Authorization");

        final JwtPreProcessingToken token = new JwtPreProcessingToken(extractor.extract(prePayload));

        return super.getAuthenticationManager().authenticate(token);

    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain, final Authentication authResult) throws IOException, ServletException {
        final SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        //context를 만들고 보관
        SecurityContextHolder.setContext(context);

        //남을 필터들에 대해 다 돌음 (필터를 선택해서 돌수도 있다)
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException failed) throws IOException, ServletException {
        //인증을 성공하지 못했기 때문에 securitycontext를 지워줘야한다.
        SecurityContextHolder.clearContext();

    }


}
