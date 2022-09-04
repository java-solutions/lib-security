package pl.javasolutions.security.oauth2;

import org.springframework.security.core.AuthenticationException;

public class OAuth2IllegalPrincipalException extends AuthenticationException {
    public OAuth2IllegalPrincipalException() {
        super("illegal authentication principal");
    }
}
