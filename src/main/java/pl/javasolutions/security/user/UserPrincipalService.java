package pl.javasolutions.security.user;

import pl.javasolutions.security.oauth2.userInfo.ProviderUserInfo;

public interface UserPrincipalService {
    UserPrincipal loadUser(ProviderUserInfo<?> providerUserInfo);
}
