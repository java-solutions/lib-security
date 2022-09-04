package pl.javasolutions.security.oauth2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
class OAuth2AuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception) throws IOException {
        log.debug("error on request: {}", request.getServletPath());
        log.error(exception.getLocalizedMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getLocalizedMessage());
    }
}
