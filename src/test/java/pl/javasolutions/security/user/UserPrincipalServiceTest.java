package pl.javasolutions.security.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import pl.javasolutions.security.config.TestPropertiesConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.javasolutions.security.oauth2.userInfo.SampleProviderGoogleUserInfo.DEFAULT_GOOGLE_OIDC_USER;

@SpringBootTest(classes = UserPrincipalServiceTest.PrincipalTestConfiguration.class)
@DisplayName("user principal service test")
class UserPrincipalServiceTest {

    @Autowired
    private UserPrincipalService userPrincipalService;

    @Test
    @DisplayName("should load user after google login")
    void shouldLoadUser() {
        UserPrincipal userPrincipal = userPrincipalService.loadUser(DEFAULT_GOOGLE_OIDC_USER);

        assertThat(userPrincipal)
                .isNotNull()
                .hasNoNullFieldsOrProperties();

    }

    @TestConfiguration
    @SpringBootConfiguration
    @Import({UserPrincipalBeanConfiguration.class, TestPropertiesConfiguration.class})
    static class PrincipalTestConfiguration {

    }
}