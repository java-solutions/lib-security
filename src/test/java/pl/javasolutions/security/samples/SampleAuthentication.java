package pl.javasolutions.security.samples;

import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;

import static pl.javasolutions.security.jwt.SampleJwtToken.DEFAULT_JWT;
import static pl.javasolutions.security.samples.SampleUser.createDefaultGrantedAuthorities;
import static pl.javasolutions.security.user.SampleUserPrincipal.DEFAULT_USER_PRINCIPAL;

public class SampleAuthentication {
    public static final Authentication DEFAULT_AUTHENTICATION = createDefaultAuthentication();

    private static TestingAuthenticationToken createDefaultAuthentication() {
        return new TestingAuthenticationToken(DEFAULT_USER_PRINCIPAL, DEFAULT_JWT, createDefaultGrantedAuthorities());
    }
}
