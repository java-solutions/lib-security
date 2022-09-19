package pl.javasolutions.security.user;

import lombok.NoArgsConstructor;

import java.util.Optional;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
class DefaultUserRepository implements UserRepository {

    @Override
    public Optional<UserDetails> findByEmail(String email) {
        return Optional.empty();
    }
}
