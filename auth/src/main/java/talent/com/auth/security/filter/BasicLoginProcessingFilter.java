package talent.com.auth.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import talent.com.auth.dto.FormLoginDto;
import talent.com.auth.security.token.PreAuthorizationToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BasicLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private AuthenticationSuccessHandler successHandler;
    private AuthenticationFailureHandler failureHandler;
    private ObjectMapper objectMapper;


    public BasicLoginProcessingFilter(final String defaultUrl, final AuthenticationSuccessHandler successHandler, final AuthenticationFailureHandler failureHandler, final ObjectMapper objectMapper) {
        super(defaultUrl);
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.objectMapper = objectMapper;
    }

    protected BasicLoginProcessingFilter(final String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);

    }


    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        final FormLoginDto formLoginDto = objectMapper.readValue(request.getReader(), FormLoginDto.class);

        final PreAuthorizationToken token = new PreAuthorizationToken(formLoginDto);

        //AuthenticationManager가 알아서 provider를 찾아 각 프로바이더로 보내준다.
        return super.getAuthenticationManager().authenticate(token);
    }


    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain, final Authentication authResult) throws IOException, ServletException {
        successHandler.onAuthenticationSuccess(request, response, authResult);
    }


    @Override
    protected void unsuccessfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException failed) throws IOException, ServletException {
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
