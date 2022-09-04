package pl.javasolutions.security.user;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository {
    Optional<UserDetails> findByEmail(String email);
}
