package pl.javasolutions.security.oauth2.userInfo;

import org.springframework.security.oauth2.core.user.OAuth2User;

class ProviderGoogleUserInfo<U extends OAuth2User> extends ProviderUserInfo<U> {

    ProviderGoogleUserInfo(U user) {
        super(user);
    }

    @Override
    public String getName() {
        return getFullName();
    }
}
