package pl.javasolutions.security.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import pl.javasolutions.security.SecurityConfigurationProperties;

import static pl.javasolutions.security.samples.Constans.DEFAULT_TOKEN_ISSUER;
import static pl.javasolutions.security.samples.Constans.DEFAULT_TOKEN_SECRET;

@TestConfiguration
public class TestPropertiesConfiguration {

    @Bean
    SecurityConfigurationProperties securityConfigurationProperties() {
        SecurityConfigurationProperties securityConfigurationProperties = new SecurityConfigurationProperties();
        SecurityConfigurationProperties.TokenProperties token = securityConfigurationProperties.getToken();
        token.setIssuer(DEFAULT_TOKEN_ISSUER);
        token.setSecret(DEFAULT_TOKEN_SECRET);
        return securityConfigurationProperties;
    }
}
