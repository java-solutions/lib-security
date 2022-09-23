package pl.javasolutions.security.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.security.Principal;

public class UserDetailsAuthenticationToken extends AbstractAuthenticationToken {

    private final Principal principal;

    /**
     * Creates a token with the supplied array of authorities.
     */
    public UserDetailsAuthenticationToken(AuthenticatedUserDetails details) {
        super(details.grantedAuthorities());
        this.principal = details;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
