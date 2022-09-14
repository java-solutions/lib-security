package pl.javasolutions.security.oauth2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@DisplayName("authentication entry point test")
class OAuth2AuthenticationEntryPointTest {

    private final OAuth2AuthenticationEntryPoint entryPoint = new OAuth2AuthenticationEntryPoint();

    @Test
    @DisplayName("should send error when invoke")
    void shouldSendError() throws IOException {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        AuthenticationException exception = mock(AuthenticationException.class);
        given(request.getServletPath()).willReturn("/some/endpoint");
        given(exception.getLocalizedMessage()).willReturn("error message");

        //when
        entryPoint.commence(request, response, exception);

        //then
        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "error message");
        verify(request).getServletPath();
        verifyNoMoreInteractions(response);
        verifyNoMoreInteractions(request);


    }
}