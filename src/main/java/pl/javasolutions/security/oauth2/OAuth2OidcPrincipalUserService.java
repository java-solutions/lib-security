package pl.javasolutions.security.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import pl.javasolutions.security.oauth2.userInfo.ProviderUserInfo;
import pl.javasolutions.security.oauth2.userInfo.ProviderUserInfoFactory;
import pl.javasolutions.security.user.UserPrincipalService;

import static lombok.AccessLevel.PROTECTED;

@RequiredArgsConstructor(access = PROTECTED)
class OAuth2OidcPrincipalUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private final OidcUserService oidcUserService;
    private final UserPrincipalService principalService;

    @Override
    public OidcUser loadUser(final OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = oidcUserService.loadUser(userRequest);
        ProviderUserInfo<OidcUser> providerUserInfo = ProviderUserInfoFactory.create(userRequest, oidcUser);
        return principalService.loadUser(providerUserInfo);
    }

}
