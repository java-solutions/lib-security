package pl.javasolutions.security.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import pl.javasolutions.security.oauth2.userInfo.ProviderUserInfo;
import pl.javasolutions.security.oauth2.userInfo.ProviderUserInfoFactory;
import pl.javasolutions.security.user.UserPrincipalService;

import static lombok.AccessLevel.PROTECTED;

@RequiredArgsConstructor(access = PROTECTED)
class OAuth2PrincipalUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final DefaultOAuth2UserService userService = new DefaultOAuth2UserService();
    private final UserPrincipalService userPrincipalService;

    @Override
    public OAuth2User loadUser(final OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oidcUser = userService.loadUser(userRequest);
        ProviderUserInfo<OAuth2User> providerUserInfo = ProviderUserInfoFactory.create(userRequest, oidcUser);
        return userPrincipalService.loadUser(providerUserInfo);
    }

}
