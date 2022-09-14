package pl.javasolutions.security.jwt;

import com.auth0.jwt.interfaces.Claim;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.jwt.Jwt;
import pl.javasolutions.security.oauth2.userInfo.ProviderUserInfo;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.oauth2.core.oidc.IdTokenClaimNames.ISS;
import static org.springframework.security.oauth2.core.oidc.StandardClaimNames.EMAIL_VERIFIED;
import static org.springframework.security.oauth2.core.oidc.StandardClaimNames.LOCALE;
import static org.springframework.security.oauth2.core.oidc.StandardClaimNames.NAME;
import static org.springframework.security.oauth2.core.oidc.StandardClaimNames.PICTURE;
import static org.springframework.security.oauth2.core.oidc.StandardClaimNames.SUB;
import static pl.javasolutions.security.samples.Constans.DEFAULT_JWT_TOKEN;
import static pl.javasolutions.security.samples.Constans.DEFAULT_JWT_TOKEN_WITH_DETAILS;
import static pl.javasolutions.security.samples.Constans.DEFAULT_TOKEN_ISSUER;
import static pl.javasolutions.security.samples.Constans.DEFAULT_TOKEN_TEST_ROLE;
import static pl.javasolutions.security.samples.SampleUser.DEFAULT_USER_DETAILS;
import static pl.javasolutions.security.jwt.SampleJwtToken.DEFAULT_JWT;
import static pl.javasolutions.security.jwt.SampleJwtToken.DEFAULT_JWT_DETAILS;
import static pl.javasolutions.security.jwt.TokenService.CLAIMS_ROLES_KEY;
import static pl.javasolutions.security.oauth2.userInfo.SampleProviderGoogleUserInfo.DEFAULT_GOOGLE_OAUTH2_USER;
import static pl.javasolutions.security.oauth2.userInfo.SampleProviderGoogleUserInfo.DEFAULT_GOOGLE_OIDC_USER;

@SpringBootTest(classes = {JwtTestConfiguration.class})
@DisplayName("jwt token service test")
class JwtTokenServiceTest {

    @Autowired
    private JwtTokenService sub;

    @ParameterizedTest(name = "{index}. {displayName}")
    @MethodSource("userInfoBase")
    @DisplayName("should create valid token base on user info")
    public void createValidToken(ProviderUserInfo<?> userInfo) {
        OidcIdToken token = sub.createToken(userInfo, null);

        assertThat(token)
                .isNotNull()
                .isInstanceOf(OidcIdToken.class)
                .as("token value is equals").extracting("tokenValue").isEqualTo(DEFAULT_JWT_TOKEN);
    }

    @ParameterizedTest(name = "{index}. {displayName}")
    @MethodSource("userInfoBase")
    @DisplayName("should create valid token base on user info and details")
    public void createValidTokenWithDetails(ProviderUserInfo<?> userInfo) {
        OidcIdToken token = sub.createToken(userInfo, DEFAULT_USER_DETAILS);

        assertThat(token)
                .isNotNull()
                .isInstanceOf(OidcIdToken.class)
                .as("token value is equals").extracting("tokenValue").isEqualTo(DEFAULT_JWT_TOKEN_WITH_DETAILS);
    }

    @Test
    @DisplayName("should decode token without any error")
    public void shouldDecodeTokenWithoutErrors() {
        Jwt decode = sub.decode(DEFAULT_JWT_TOKEN);

        assertThat(decode)
                .isNotNull()
                .isEqualTo(DEFAULT_JWT);

        assertThat(decode.getClaims())
                .isNotNull()
                .containsEntry(ISS, DEFAULT_TOKEN_ISSUER)
                .containsKeys(EMAIL_VERIFIED, NAME, LOCALE, SUB)
                .doesNotContainKeys(PICTURE, CLAIMS_ROLES_KEY);

    }

    @Test
    @DisplayName("should decode token details without any error")
    public void shouldDecodeTokenDetailsWithoutErrors() {
        Jwt decode = sub.decode(DEFAULT_JWT_TOKEN_WITH_DETAILS);

        assertThat(decode)
                .isNotNull()
                .isEqualTo(DEFAULT_JWT_DETAILS);

        assertThat(decode.getClaims())
                .isNotNull()
                .containsEntry(ISS, DEFAULT_TOKEN_ISSUER)
                .containsKeys(EMAIL_VERIFIED, NAME, LOCALE, SUB, CLAIMS_ROLES_KEY);

        assertThat((Object) decode.getClaim(CLAIMS_ROLES_KEY))
                .isNotNull()
                .isInstanceOf(Claim.class)
                .extracting(value -> ((Claim) value).asList(String.class)).asList()
                .contains(DEFAULT_TOKEN_TEST_ROLE);

    }

    static Stream<Arguments> userInfoBase() {
        return Stream.of(
                Arguments.of(DEFAULT_GOOGLE_OAUTH2_USER),
                Arguments.of(DEFAULT_GOOGLE_OIDC_USER)
        );
    }

}