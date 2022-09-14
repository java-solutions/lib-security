package pl.javasolutions.security.oauth2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.javasolutions.security.SecurityConfigurationProperties.OAuth2Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@DisplayName("authentication failure handler test")
class OAuth2AuthenticationFailureHandlerTest {

    @Mock
    private OAuth2Properties properties;

    @Mock
    private RedirectStrategy redirectStrategy;

    @InjectMocks
    private OAuth2AuthenticationFailureHandler handler;

    @Test
    @DisplayName("should handler failure and redirect to valid url with authentication error")
    void shouldHandleFailure() throws IOException {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        AuthenticationException exception = mock(AuthenticationException.class);
        given(properties.getRedirectUrl()).willReturn("http://localhost:8080");

        // when
        handler.onAuthenticationFailure(request, response, exception);

        // then
        verify(redirectStrategy).sendRedirect(request, response, "http://localhost:8080?error=authentication_error");
    }
}