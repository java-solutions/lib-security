package pl.javasolutions.security.oauth2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.util.UriComponentsBuilder;
import pl.javasolutions.security.SecurityConfigurationProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static lombok.AccessLevel.PROTECTED;

@Slf4j
@RequiredArgsConstructor(access = PROTECTED)
class OAuth2AuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final SecurityConfigurationProperties.OAuth2Properties properties;
    private final RedirectStrategy redirectStrategy;

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception) throws IOException {
        log.error("error on authentication", exception);
        String targetUrl = UriComponentsBuilder.fromUriString(determineTargetUrl())
                .queryParam("error", "authentication_error")
                .build().toUriString();
        this.redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    // TODO: redirect from request
    protected String determineTargetUrl() {
        return properties.getRedirectUrl();
    }
}
