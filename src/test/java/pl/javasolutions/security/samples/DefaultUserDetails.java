package pl.javasolutions.security.samples;

import lombok.Data;
import pl.javasolutions.security.user.UserDetails;

import java.util.Collection;

@Data
class DefaultUserDetails implements UserDetails {
    private final String id;
    private final Collection<String> authorities;
}
