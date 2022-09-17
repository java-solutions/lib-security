package pl.javasolutions.security.jwt;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import pl.javasolutions.security.SecurityConfigurationProperties;

import java.time.Clock;

public class SecurityTokenBeanConfiguration {

    @Bean
    @ConditionalOnMissingBean
    TokenService defaultTokenService(SecurityConfigurationProperties properties, Clock clock) {
        return new JwtTokenService(properties.getToken(), clock);
    }

    @Bean
    @ConditionalOnMissingBean
    Clock defaultClock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    @ConditionalOnMissingBean
    Converter<Jwt, JwtAuthenticationToken> jwtAuthenticationTokenConverter(SecurityConfigurationProperties properties) {
        return new JwtAuthenticationConverter(properties.getToken());
    }

}
