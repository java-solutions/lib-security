package pl.javasolutions.security.jwt;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import pl.javasolutions.security.ClockRepository;
import pl.javasolutions.security.SecurityConfigurationProperties;

public class SecurityTokenBeanConfiguration {

    @Bean
    @ConditionalOnMissingBean
    TokenService defaultTokenService(SecurityConfigurationProperties properties, ClockRepository clockRepository) {
        return new JwtTokenService(properties.getToken(), clockRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    Converter<Jwt, JwtAuthenticationToken> jwtAuthenticationTokenConverter(SecurityConfigurationProperties properties) {
        return new JwtAuthenticationConverter(properties.getToken());
    }

    @Bean
    @ConditionalOnMissingBean
    ClockRepository clockRepository() {
        return new LocalDateClockRepository();
    }

}
