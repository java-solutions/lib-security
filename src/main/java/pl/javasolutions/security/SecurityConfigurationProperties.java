package pl.javasolutions.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "js.app.security")
public class SecurityConfigurationProperties {

    private final OAuth2Properties oauth2 = new OAuth2Properties();
    private final TokenProperties token = new TokenProperties();

    @Data
    public static class OAuth2Properties {
        private String redirectUrl = "http://localhost:3000";
        private String tokenParam = "token";
    }

    @Data
    public static class TokenProperties {
        private String issuer = "javasolutions";
        private String secret = "secret";
        private String rolePrefix = "ROLE";
        private List<String> defaultRoles = List.of("demo");

    }
}
