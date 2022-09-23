package pl.javasolutions.security.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.Jwt;
import pl.javasolutions.security.config.TestPropertiesConfiguration;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.javasolutions.security.jwt.SampleJwtToken.DEFAULT_JWT;
import static pl.javasolutions.security.jwt.SampleJwtToken.DEFAULT_JWT_DETAILS;
import static pl.javasolutions.security.samples.Constans.DEFAULT_TOKEN_DEFAULT_AUTHORITY_ROLE;
import static pl.javasolutions.security.samples.Constans.DEFAULT_TOKEN_TEST_AUTHORITY_ROLE;

@SpringBootTest(classes = {SecurityTokenBeanConfiguration.class, TestPropertiesConfiguration.class})
@DisplayName("jwt authentication converter tests")
class JwtAuthenticationConverterTest {

    @Autowired
    private JwtAuthenticationConverter sub;

    @ParameterizedTest
    @MethodSource("tokens")
    @DisplayName("should convert default jwt to jwt authentication token")
    void shouldConvertJwtToJwtAuthenticationToken(Jwt token, String authorityName) {
        UserDetailsAuthenticationToken convertedToken = sub.convert(token);

        assertNotNull(convertedToken, "converter cannot be null");
        assertEquals(token.getSubject(), convertedToken.getName(), "subject must be the same");
        assertNotNull(convertedToken.getAuthorities(), "authorities should be not null");
        assertEquals(1, convertedToken.getAuthorities().size(), "authorities should have one authority");
        assertTrue(convertedToken.getAuthorities().stream().findFirst().isPresent());
        assertEquals(authorityName, convertedToken.getAuthorities().stream().findFirst().get().getAuthority());
        assertTrue(convertedToken.isAuthenticated(), "token should be authenticated");

    }

    public static Stream<Arguments> tokens() {
        return Stream.of(
                Arguments.of(DEFAULT_JWT, DEFAULT_TOKEN_DEFAULT_AUTHORITY_ROLE),
                Arguments.of(DEFAULT_JWT_DETAILS, DEFAULT_TOKEN_TEST_AUTHORITY_ROLE)
        );
    }
}