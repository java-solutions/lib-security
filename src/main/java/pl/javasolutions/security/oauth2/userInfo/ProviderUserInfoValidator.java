package pl.javasolutions.security.oauth2.userInfo;

import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class ProviderUserInfoValidator {
    public static void validateUserInfo(final ProviderUserInfo<?> providerUserInfo) {
        Objects.requireNonNull(providerUserInfo);
        Objects.requireNonNull(providerUserInfo.getEmail());
    }
}
