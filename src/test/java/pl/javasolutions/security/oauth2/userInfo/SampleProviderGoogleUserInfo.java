package pl.javasolutions.security.oauth2.userInfo;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import static pl.javasolutions.security.samples.SampleUser.DEFAULT_OAUTH2_USER;
import static pl.javasolutions.security.samples.SampleUser.DEFAULT_OIDC_USER;

public class SampleProviderGoogleUserInfo {

    public static final ProviderUserInfo<OAuth2User> DEFAULT_GOOGLE_OAUTH2_USER = createDefaultGoogleOAuth2User();
    public static final ProviderUserInfo<OidcUser> DEFAULT_GOOGLE_OIDC_USER = createDefaultGoogleOidcUser();

    public static <T extends OAuth2User> ProviderUserInfo<T> createGoogleUser(T user) {
        return new ProviderGoogleUserInfo<>(user);
    }

    private static ProviderUserInfo<OAuth2User> createDefaultGoogleOAuth2User() {
        return createGoogleUser(DEFAULT_OAUTH2_USER);
    }

    private static ProviderUserInfo<OidcUser> createDefaultGoogleOidcUser() {
        return createGoogleUser(DEFAULT_OIDC_USER);
    }

}