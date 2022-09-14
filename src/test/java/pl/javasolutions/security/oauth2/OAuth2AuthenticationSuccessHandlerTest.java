package pl.javasolutions.security.oauth2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.javasolutions.security.SecurityConfigurationProperties.OAuth2Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static pl.javasolutions.security.samples.Constans.DEFAULT_JWT_TOKEN_WITH_DETAILS;
import static pl.javasolutions.security.samples.SampleAuthentication.DEFAULT_AUTHENTICATION;

@ExtendWith(SpringExtension.class)
@DisplayName("authentication success handler test")
class OAuth2AuthenticationSuccessHandlerTest {

    @Mock
    private OAuth2Properties properties;

    @Mock
    private RedirectStrategy redirectStrategy;

    @InjectMocks
    private OAuth2AuthenticationSuccessHandler handler;

    @Test
    @DisplayName("should handle success authentication and redirect to provided url with token")
    void shouldHandleSuccessAuthentication() throws IOException {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        given(properties.getRedirectUrl()).willReturn("http://localhost:8080");
        given(properties.getTokenParam()).willReturn("token");

        // when
        handler.onAuthenticationSuccess(request, response, DEFAULT_AUTHENTICATION);

        // then
        verify(redirectStrategy).sendRedirect(request, response, "http://localhost:8080?token=" + DEFAULT_JWT_TOKEN_WITH_DETAILS);
    }
}