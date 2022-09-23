package pl.javasolutions.security.user;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

import static lombok.AccessLevel.PROTECTED;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = PROTECTED, staticName = "create")
public class UserPrincipal implements OidcUser {

    private final OidcIdToken oidcIdToken;
    private final UserDetails userDetails;

    public Optional<UserDetails> getUserDetails() {
        return Optional.ofNullable(userDetails);
    }

    @Override
    public Map<String, Object> getClaims() {
        return oidcIdToken.getClaims();
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return new OidcUserInfo(getClaims());
    }

    @Override
    public OidcIdToken getIdToken() {
        return oidcIdToken;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oidcIdToken.getClaims();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getUserDetails()
                .map(UserDetails::grantedAuthorities)
                .orElseGet(HashSet::new);
    }

    @Override
    public String getName() {
        return oidcIdToken.getFullName();
    }
}
