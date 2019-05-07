package talent.com.auth.security.filter;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public class FilterSkipMatchar implements RequestMatcher {


    private final OrRequestMatcher orRequestMatcher;
    private final RequestMatcher requestMatcher;

    public FilterSkipMatchar(List<String> pathToSkip, String processingPath) {
        orRequestMatcher = new OrRequestMatcher(
                pathToSkip.stream()
                        .map(p -> new AntPathRequestMatcher(p))
                        .collect(Collectors.toList())
        );

        requestMatcher = new AntPathRequestMatcher(processingPath);
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        return !orRequestMatcher.matches(request) && requestMatcher.matches(request);
    }
}
