package pl.javasolutions.security.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import pl.javasolutions.security.ClockRepository;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static pl.javasolutions.security.samples.Constans.DEFAULT_DATE;
import static pl.javasolutions.security.samples.Constans.DEFAULT_TOKEN_CREATE_DATE;

@TestConfiguration
public class TestClockConfiguration {

    @Bean
    @ConditionalOnMissingBean
    ClockRepository clockRepository() {
        return new TestClockRepository();
    }

    private static class TestClockRepository implements ClockRepository {

        @Override
        public LocalDateTime now() {
            return LocalDateTime.from(DEFAULT_TOKEN_CREATE_DATE);
        }

        @Override
        public Clock getCurrentClock() {
            return Clock.fixed(DEFAULT_DATE.toInstant(), ZoneOffset.UTC);
        }
    }
}
