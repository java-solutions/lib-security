package pl.javasolutions.security.oauth2.userInfo;

import lombok.experimental.UtilityClass;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

import static pl.javasolutions.security.oauth2.userInfo.ProviderUserInfo.GOOGLE;

@UtilityClass
public class ProviderUserInfoFactory {

    @SuppressWarnings("SwitchStatementWithTooFewBranches") //TODO: remove when add new provider
    public static <U extends OAuth2User> ProviderUserInfo<U> create(final OAuth2UserRequest request, final U oidcUser) {
        String registrationId = request.getClientRegistration().getRegistrationId();

        return switch (registrationId) {
            case GOOGLE -> new ProviderGoogleUserInfo<>(oidcUser);
            default -> throw new IllegalStateException("invalid providerClientId: " + registrationId); // TODO: custom exception
        };
    }
}
