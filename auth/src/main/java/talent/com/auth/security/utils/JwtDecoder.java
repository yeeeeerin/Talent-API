package talent.com.auth.security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.extern.slf4j.Slf4j;
import talent.com.auth.domain.MemberDetail;
import talent.com.auth.exception.InvaildJwtException;

import java.util.Map;
import java.util.Optional;

@Slf4j
public class JwtDecoder {

    public static MemberDetail decodeJwt(String token) {

        DecodedJWT decodedJWT = JwtDecoder.isVaildToken(token).orElseThrow(() -> new InvaildJwtException("유요한 토큰 아님"));

        Map<String, Claim> claims = decodedJWT.getClaims();

        return MemberDetail.from(claims.get("USERNAME").asString(), claims.get("USER_ROLE").asString());

    }

    private static Optional<DecodedJWT> isVaildToken(String token) {
        DecodedJWT jwt;

        try {
            Algorithm algorithm = Algorithm.HMAC256("jwttest");
            JWTVerifier verifier = JWT.require(algorithm).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            JwtDecoder.log.error(e.getMessage());
            throw new InvaildJwtException("올바르지 않은 토큰 입니다.");
        }

        return Optional.ofNullable(jwt);
    }


}
