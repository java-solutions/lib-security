package pl.javasolutions.security.oauth2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;
import pl.javasolutions.security.SecurityConfigurationProperties;
import pl.javasolutions.security.user.UserPrincipal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static lombok.AccessLevel.PROTECTED;

@Slf4j
@RequiredArgsConstructor(access = PROTECTED)
class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final SecurityConfigurationProperties.OAuth2Properties properties;
    private final RedirectStrategy redirectStrategy;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (response.isCommitted()) {
            log.debug("Response has already been committed");
            return;
        }

        if (!(authentication.getPrincipal() instanceof UserPrincipal userPrincipal)) {
            throw new OAuth2IllegalPrincipalException();
        }

        handle(request, response, userPrincipal);
    }

    protected void handle(HttpServletRequest request, HttpServletResponse response, UserPrincipal userPrincipal) throws IOException {
        String targetUrl = UriComponentsBuilder.fromUriString(determineTargetUrl())
                .queryParam(properties.getTokenParam(), userPrincipal.getIdToken().getTokenValue())
                .build().toUriString();
        this.redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    // TODO: redirect from request
    protected String determineTargetUrl() {
        return properties.getRedirectUrl();
    }
}
