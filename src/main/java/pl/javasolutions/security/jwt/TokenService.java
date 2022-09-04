package pl.javasolutions.security.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import pl.javasolutions.security.oauth2.userInfo.ProviderUserInfo;

/**
 * Supports authorization token.
 * <p>
 * The implementation can support the token in any form.
 * The default implementation is the JWT implementation.
 */
public interface TokenService extends JwtDecoder {

    String CLAIMS_ROLES_KEY = "roles";
    /**
     * Create token from claims map.
     *
     * @return token string.
     */
    OidcIdToken createToken(ProviderUserInfo<?> userInfo, UserDetails userDetails);

    OidcIdToken createToken(ProviderUserInfo<?> userInfo);
}
