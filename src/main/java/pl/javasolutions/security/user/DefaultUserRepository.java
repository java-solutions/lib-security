package pl.javasolutions.security.user;

import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
class DefaultUserRepository implements UserRepository {

    @Override
    public Optional<UserDetails> findByEmail(final String email) {
        return Optional.empty();
    }
}
