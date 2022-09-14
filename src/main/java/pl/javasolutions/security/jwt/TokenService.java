package pl.javasolutions.security.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import pl.javasolutions.security.oauth2.userInfo.ProviderUserInfo;


public interface TokenService extends JwtDecoder {

    String CLAIMS_ROLES_KEY = "roles";

    OidcIdToken createToken(ProviderUserInfo<?> userInfo, UserDetails userDetails);

    OidcIdToken createToken(ProviderUserInfo<?> userInfo);
}
