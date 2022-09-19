package pl.javasolutions.security.user;

import java.util.Collection;

public interface UserDetails {
    String getId();

    Collection<String> getAuthorities();

}
