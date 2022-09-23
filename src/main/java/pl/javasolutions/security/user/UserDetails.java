package pl.javasolutions.security.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public interface UserDetails {
    String getId();

    Collection<String> getAuthorities();

    default Collection<? extends GrantedAuthority> grantedAuthorities() {
        if (getAuthorities().isEmpty()) {
            return Collections.emptySet();
        }
        return getAuthorities().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
