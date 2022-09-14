package pl.javasolutions.security.oauth2.userInfo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static pl.javasolutions.security.oauth2.userInfo.SampleProviderGoogleUserInfo.DEFAULT_GOOGLE_OAUTH2_USER;
import static pl.javasolutions.security.oauth2.userInfo.SampleProviderGoogleUserInfo.DEFAULT_GOOGLE_OIDC_USER;

@DisplayName("provider user info validator")
class ProviderUserInfoValidatorTest {

    @ParameterizedTest
    @MethodSource("providerUserInfo")
    @DisplayName("should validate provider user info with out error")
    void shouldValidateProviderUserInfoWithoutError(ProviderUserInfo<?> providerUserInfo) {
        ProviderUserInfoValidator.validateUserInfo(providerUserInfo);
    }

    public static Stream<Arguments> providerUserInfo() {
        return Stream.of(
                Arguments.of(DEFAULT_GOOGLE_OIDC_USER),
                Arguments.of(DEFAULT_GOOGLE_OAUTH2_USER)
        );
    }
}