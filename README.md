
# Security Lib

Autoconfiguration of application security. Allows support for third-party providers such as Google to obtain an authorization token.

Allows configuration of additional token parameters through a shared user details repository, which is used to specify additional roles from any persistence layer.

With additional configuration, it allows you to specify basic parameters such as issuer or default permissions for non-registered users.


## Badges

[![GPLv3 License](https://img.shields.io/badge/License-GPL%20v3-yellow.svg)](https://opensource.org/licenses/)


## Installation

Use by maven dependency

```xml
<dependency>
    <groupId>pl.javasolutions</groupId>
    <artifactId>security</artifactId>
    <version>${js.security.version}</version>
</dependency>
```

## Usage/Examples

### Base configuration

```java
import org.springframework.context.annotation.Configuration;
import pl.javasolutions.security.oauth2.EnableOAuth2Login;

@Configuration
@EnableOAuth2Login
class SecurityConfig {

}
```

in application.yml

```yml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            redirect-uri: "{baseUrl}/login/oauth2/callback/{registrationId}"
            client-id: ${YOUR_OWN_CLIENT_ID}
            client-secret: ${YOUR_OWN_CLIENT_SECRET}
            scope:
              - email
              - profile
              - openid
js:
  app:
    security:
      token:
        secret: ${JWT_TOKEN_SECRET}
        issuer: ${JWT_ISSUER}
      oauth2:
        redirect-url: ${OAUTH2_REDIRECT_URL}
```