package pl.javasolutions.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import pl.javasolutions.security.SecurityConfigurationProperties;
import pl.javasolutions.security.oauth2.userInfo.ProviderUserInfo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNullElse;
import static lombok.AccessLevel.PROTECTED;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Slf4j
@RequiredArgsConstructor(access = PROTECTED)
class JwtTokenService implements TokenService {

    private final static String DEFAULT_CLAIMS_LOCALE = "pl";

    private final SecurityConfigurationProperties.TokenProperties tokenProperties;

    @Override
    public OidcIdToken createToken(final ProviderUserInfo<?> userInfo) {
        return createToken(userInfo, null);
    }

    @Override
    public OidcIdToken createToken(ProviderUserInfo<?> userInfo, UserDetails userDetails) {
        Algorithm algorithm = Algorithm.HMAC512(tokenProperties.getSecret());
        Map<String, Object> claims = createClaims(userInfo, userDetails);

        String token = JWT.create()
                .withIssuer(tokenProperties.getIssuer())
                .withSubject(userInfo.getSubject())
                .withIssuedAt(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())
                .withExpiresAt(LocalDateTime.now().plusHours(1).atZone(ZoneId.systemDefault()).toInstant())
                .withPayload(claims)
                .sign(algorithm);

        return new OidcIdToken(token, userInfo.getIssuedAt(), userInfo.getExpiresAt(), claims);
    }

    @Override
    public Jwt decode(final String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(tokenProperties.getSecret());
            DecodedJWT decodedToken = JWT.require(algorithm)
                    .withIssuer(tokenProperties.getIssuer())
                    .build().verify(token);

            return Jwt.withTokenValue(token)
                    .header("alg", decodedToken.getToken())
                    .claims(claims -> claims.putAll(decodedToken.getClaims()))
                    .issuedAt(decodedToken.getIssuedAtAsInstant())
                    .expiresAt(decodedToken.getExpiresAtAsInstant())
                    .subject(decodedToken.getSubject())
                    .issuer(decodedToken.getIssuer())
                    .build();

        } catch (JWTVerificationException ex) {
            log.error("error on decode token", ex);
            throw new InvalidBearerTokenException("token verification exception", ex);
        } catch (Exception ex) {
            log.error("error on decode token", ex);
            throw new AuthenticationServiceException("decode token exception", ex);
        }
    }

    private Map<String, Object> createClaims(ProviderUserInfo<?> userInfo, UserDetails userDetails) {
        log.debug("create claims with userInfo: {}", userInfo);
        Map<String, Object> claims = new HashMap<>();

        claims.put(StandardClaimNames.EMAIL,
                requireNonNull(userInfo.getEmail(), "email must be not null when creating claims")
        );

        claims.put(StandardClaimNames.EMAIL_VERIFIED,
                requireNonNullElse(userInfo.getEmailVerified(), false)
        );

        claims.put(StandardClaimNames.NAME,
                requireNonNull(userInfo.getName(), "name must be not null when creating claims")
        );

        claims.put(StandardClaimNames.LOCALE,
                requireNonNullElse(userInfo.getLocale(), DEFAULT_CLAIMS_LOCALE)
        );

        if (isNotBlank(userInfo.getPicture())) {
            claims.put(StandardClaimNames.PICTURE, userInfo.getPicture());
        }

        if (nonNull(userDetails)) {
            claims.put(StandardClaimNames.SUB, userDetails.getUsername());
            claims.put(CLAIMS_ROLES_KEY, userDetails.getAuthorities());
        }

        return claims;
    }
}
