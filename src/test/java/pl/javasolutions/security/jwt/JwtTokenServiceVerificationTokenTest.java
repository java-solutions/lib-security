package pl.javasolutions.security.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import pl.javasolutions.security.config.TestPropertiesConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.javasolutions.security.samples.Constans.DEFAULT_JWT_TOKEN;
import static pl.javasolutions.security.samples.Constans.DEFAULT_JWT_TOKEN_WITH_DETAILS;

@SpringBootTest(classes = {SecurityTokenBeanConfiguration.class, TestPropertiesConfiguration.class})
@DisplayName("jwt token service validation test")
class JwtTokenServiceVerificationTokenTest {

    @Autowired
    private JwtTokenService sub;

    @ParameterizedTest
    @ValueSource(strings = {DEFAULT_JWT_TOKEN, DEFAULT_JWT_TOKEN_WITH_DETAILS})
    @DisplayName("should decode token with error InvalidBearerTokenException")
    public void shouldDecodeTokenWithoutErrors(String token) {

        Exception exception = assertThrows(InvalidBearerTokenException.class, () ->
                sub.decode(token));

        assertThat(exception)
                .isNotNull();
    }
}