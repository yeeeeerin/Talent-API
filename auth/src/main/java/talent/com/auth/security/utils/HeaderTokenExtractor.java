package talent.com.auth.security.utils;


import org.springframework.util.StringUtils;
import talent.com.auth.exception.InvaildJwtException;


public class HeaderTokenExtractor {

    private static final String HEADER_PREFIX = "Bearer ";

    public static String extract(String header) {

        //문자열이 비어있는지
        if (StringUtils.isEmpty(header) || header.length() < HeaderTokenExtractor.HEADER_PREFIX.length()) {
            throw new InvaildJwtException("올바른 토큰 정보가 아닙니다");
        }


        return header.substring(HeaderTokenExtractor.HEADER_PREFIX.length());

    }

}
