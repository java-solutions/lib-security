package pl.javasolutions.security.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import pl.javasolutions.security.ClockRepository;

import java.time.Clock;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.javasolutions.security.samples.Constans.DEFAULT_JWT_TOKEN;
import static pl.javasolutions.security.samples.Constans.DEFAULT_JWT_TOKEN_WITH_DETAILS;

@SpringBootTest(classes = {JwtTestConfiguration.class, JwtTokenServiceVerificationTokenTest.TimeConfiguration.class})
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

    @TestConfiguration
    static class TimeConfiguration {
        @Bean
        @Primary
        ClockRepository testClockRepository() {
            return new TestClockRepository();
        }
    }

    private static class TestClockRepository implements ClockRepository {

        @Override
        public LocalDateTime now() {
            return LocalDateTime.now();
        }

        @Override
        public Clock getCurrentClock() {
            return Clock.systemDefaultZone();
        }
    }
}