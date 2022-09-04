package pl.javasolutions.security.oauth2.userInfo;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@ToString
@EqualsAndHashCode
public abstract class ProviderUserInfo<U extends OAuth2User> implements OidcUser {

    public static final String GOOGLE = "google";

    private final U providerUserInfo;
    private final OidcUserInfo userInfo;

    public ProviderUserInfo(U oidcUser) {
        this.providerUserInfo = oidcUser;
        this.userInfo = new OidcUserInfo(oidcUser.getAttributes());
    }

    @Override
    public Map<String, Object> getClaims() {
        return providerUserInfo.getAttributes();
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return userInfo;
    }

    @Override
    public OidcIdToken getIdToken() {

        if (providerUserInfo instanceof OidcUser oidcUser) {
            return oidcUser.getIdToken();
        }

        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return providerUserInfo.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return providerUserInfo.getAuthorities();
    }


}
