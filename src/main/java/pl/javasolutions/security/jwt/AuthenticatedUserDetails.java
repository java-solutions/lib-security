package pl.javasolutions.security.jwt;

import lombok.Data;
import lombok.With;
import pl.javasolutions.security.user.UserDetails;

import java.security.Principal;
import java.util.Collection;

@Data
public final class AuthenticatedUserDetails implements UserDetails, Principal {
    private final String id;

    @With
    private final Collection<String> authorities;

    @Override
    public String getName() {
        return id;
    }
}
