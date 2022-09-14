package pl.javasolutions.security.jwt;

import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

import static org.springframework.security.oauth2.jwt.JoseHeaderNames.ALG;
import static org.springframework.security.oauth2.jwt.JoseHeaderNames.TYP;
import static pl.javasolutions.security.samples.Constans.DEFAULT_JWT_TOKEN;
import static pl.javasolutions.security.samples.Constans.DEFAULT_JWT_TOKEN_WITH_DETAILS;
import static pl.javasolutions.security.samples.Constans.DEFAULT_TOKEN_ALGORITHM;
import static pl.javasolutions.security.samples.Constans.DEFAULT_TOKEN_CREATE_DATE;
import static pl.javasolutions.security.samples.Constans.DEFAULT_TOKEN_EXPIRATION_DATE;
import static pl.javasolutions.security.samples.Constans.DEFAULT_TOKEN_ISSUER;
import static pl.javasolutions.security.samples.Constans.DEFAULT_TOKEN_TEST_ROLE;
import static pl.javasolutions.security.samples.Constans.DEFAULT_TOKEN_TYPE;
import static pl.javasolutions.security.jwt.TokenService.CLAIMS_ROLES_KEY;

public class SampleJwtToken {

    public static final Jwt DEFAULT_JWT = createDefaultJwt();
    public static final Jwt DEFAULT_JWT_DETAILS = createDefaultJwt(List.of(DEFAULT_TOKEN_TEST_ROLE));

    private static Jwt createDefaultJwt() {
        return Jwt.withTokenValue(DEFAULT_JWT_TOKEN)
                .header(ALG, DEFAULT_TOKEN_ALGORITHM)
                .header(TYP, DEFAULT_TOKEN_TYPE)
                .issuer(DEFAULT_TOKEN_ISSUER)
                .issuedAt(DEFAULT_TOKEN_CREATE_DATE.toInstant())
                .expiresAt(DEFAULT_TOKEN_EXPIRATION_DATE.toInstant())
                .build();
    }

    private static Jwt createDefaultJwt(List<String> roles) {
        return Jwt.withTokenValue(DEFAULT_JWT_TOKEN_WITH_DETAILS)
                .header(ALG, DEFAULT_TOKEN_ALGORITHM)
                .header(TYP, DEFAULT_TOKEN_TYPE)
                .issuer(DEFAULT_TOKEN_ISSUER)
                .issuedAt(DEFAULT_TOKEN_CREATE_DATE.toInstant())
                .expiresAt(DEFAULT_TOKEN_EXPIRATION_DATE.toInstant())
                .claim(CLAIMS_ROLES_KEY, roles)
                .build();
    }

}
