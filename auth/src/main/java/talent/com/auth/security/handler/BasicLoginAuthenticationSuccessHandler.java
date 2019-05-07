package talent.com.auth.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import talent.com.auth.domain.MemberDetail;
import talent.com.auth.dto.TokenDto;
import talent.com.auth.security.utils.JwtFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class BasicLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;

    private final JwtFactory jwtFactory;

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {
        final MemberDetail memberDetail = (MemberDetail) authentication.getPrincipal();
        final String token = jwtFactory.generateToken(memberDetail);
        final TokenDto tokenDto = TokenDto.of(token);

        makeResponse(response, tokenDto);
    }

    private void makeResponse(final HttpServletResponse response, final TokenDto tokenDto) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(objectMapper.writeValueAsString(tokenDto));
    }
}
