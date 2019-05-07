package talent.com.auth.security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import talent.com.auth.domain.MemberDetail;

@Slf4j
public class JwtFactory {

    private final String tokenIssuer;
    private final String tokenSigningKey;

    public JwtFactory(final String tokenIssuer, final String tokenSigningKey) {
        this.tokenIssuer = tokenIssuer;
        this.tokenSigningKey = tokenSigningKey;
    }

    public String generateToken(final MemberDetail memberDetail) {
        final String token;

        token = JWT.create()
                .withIssuer(tokenIssuer)
                .withClaim("EMAIL", memberDetail.getUsername())
                .withClaim("ROLE", memberDetail.getRole())
                .sign(Algorithm.HMAC256(tokenSigningKey));

        JwtFactory.log.info("token -- " + token);

        return token;

    }

}
