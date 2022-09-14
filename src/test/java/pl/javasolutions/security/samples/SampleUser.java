package pl.javasolutions.security.samples;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static pl.javasolutions.security.samples.Constans.DEFAULT_JWT_TOKEN_WITH_DETAILS;
import static pl.javasolutions.security.samples.Constans.DEFAULT_TOKEN_CREATE_DATE;
import static pl.javasolutions.security.samples.Constans.DEFAULT_TOKEN_EXPIRATION_DATE;
import static pl.javasolutions.security.samples.Constans.DEFAULT_TOKEN_ISSUER;
import static pl.javasolutions.security.samples.Constans.DEFAULT_TOKEN_TEST_ROLE;
import static pl.javasolutions.security.samples.Constans.DEFAULT_USER_DETAILS_USERNAME;
import static pl.javasolutions.security.samples.Constans.DEFAULT_USER_EMAIL;
import static pl.javasolutions.security.samples.Constans.DEFAULT_USER_FULL_NAME;
import static pl.javasolutions.security.samples.Constans.DEFAULT_USER_ID;

public class SampleUser {
    public static final OidcIdToken DEFAULT_OIDC_ID_TOKEN = createOidcIdToken();
    public static final OAuth2User DEFAULT_OAUTH2_USER = createDefaultOAuth2User();
    public static final OidcUser DEFAULT_OIDC_USER = createDefaultOidcUser();
    public static final UserDetails DEFAULT_USER_DETAILS = createDefaultUserDetails();

    private static OidcIdToken createOidcIdToken() {
        return new OidcIdToken(DEFAULT_JWT_TOKEN_WITH_DETAILS, DEFAULT_TOKEN_CREATE_DATE.toInstant(), DEFAULT_TOKEN_EXPIRATION_DATE.toInstant(), defaultAttributes());
    }

    private static OAuth2User createDefaultOAuth2User() {
        return new DefaultOAuth2User(createDefaultGrantedAuthorities(), defaultAttributes(), StandardClaimNames.SUB);
    }

    private static OidcUser createDefaultOidcUser() {
        return new DefaultOidcUser(createDefaultGrantedAuthorities(), DEFAULT_OIDC_ID_TOKEN, StandardClaimNames.SUB);
    }

    private static Map<String, Object> defaultAttributes() {
        return Map.ofEntries(
                Map.entry(IdTokenClaimNames.ISS, DEFAULT_TOKEN_ISSUER),
                Map.entry(StandardClaimNames.SUB, DEFAULT_USER_ID),
                Map.entry(StandardClaimNames.EMAIL, DEFAULT_USER_EMAIL),
                Map.entry(StandardClaimNames.NAME, DEFAULT_USER_FULL_NAME),
                Map.entry(IdTokenClaimNames.IAT, DEFAULT_TOKEN_CREATE_DATE.toInstant()),
                Map.entry(IdTokenClaimNames.EXP, DEFAULT_TOKEN_EXPIRATION_DATE.toInstant())
        );
    }

    private static UserDetails createDefaultUserDetails() {
        Collection<? extends GrantedAuthority> authorities = createDefaultGrantedAuthorities();
        return new User(DEFAULT_USER_DETAILS_USERNAME, "NIEWAZNE", true, true, true, true, authorities);
    }

    static List<GrantedAuthority> createDefaultGrantedAuthorities() {
        return List.of(
                new SimpleGrantedAuthority(DEFAULT_TOKEN_TEST_ROLE)
        );
    }

}
