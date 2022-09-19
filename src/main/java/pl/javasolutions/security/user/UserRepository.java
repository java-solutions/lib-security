package pl.javasolutions.security.user;

import java.util.Optional;

public interface UserRepository {
    Optional<UserDetails> findByEmail(String email);
}
