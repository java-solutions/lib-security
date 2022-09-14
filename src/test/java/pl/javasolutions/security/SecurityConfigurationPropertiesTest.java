package pl.javasolutions.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = SecurityConfigurationPropertiesTest.PropertyTestConfiguration.class)
@DisplayName("security configuration properties mapping test")
public class SecurityConfigurationPropertiesTest {

    @Autowired
    private SecurityConfigurationProperties properties;

    @Test
    @DisplayName("should create valid property configuration")
    void shouldCreateValidPropertyConfiguration() {
        assertThat(properties)
                .isNotNull()
                .hasNoNullFieldsOrProperties();

        assertThat(properties.getOauth2())
                .isNotNull()
                .hasFieldOrPropertyWithValue("redirectUrl", "http://localhost:8081")
                .hasFieldOrPropertyWithValue("tokenParam", "test");

        assertThat(properties.getToken())
                .isNotNull()
                .hasFieldOrPropertyWithValue("issuer", "test-issuer")
                .hasFieldOrPropertyWithValue("secret", "secret-test")
                .hasFieldOrPropertyWithValue("rolePrefix", "GRANTED_ROLE")
                .hasFieldOrPropertyWithValue("defaultRoles", List.of("test", "default"));
    }

    @TestConfiguration
    @SpringBootConfiguration
    @EnableConfigurationProperties(SecurityConfigurationProperties.class)
    static class PropertyTestConfiguration {

    }
}