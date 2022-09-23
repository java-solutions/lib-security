package pl.javasolutions.security.jwt;

import com.auth0.jwt.interfaces.Claim;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;
import pl.javasolutions.security.SecurityConfigurationProperties.TokenProperties;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PROTECTED;
import static pl.javasolutions.security.jwt.TokenService.CLAIMS_ROLES_KEY;

@RequiredArgsConstructor(access = PROTECTED)
class JwtAuthenticationConverter implements Converter<Jwt, UserDetailsAuthenticationToken> {

    private final TokenProperties properties;

    @Override
    public UserDetailsAuthenticationToken convert(Jwt source) {

        Set<String> roles = ofNullable(source.getClaim(CLAIMS_ROLES_KEY))
                .map(getClaimsFromJwt())
                .map(this::mapAuthorityRoleName)
                .orElseGet(Set::of);

        AuthenticatedUserDetails authenticatedUserDetails = new AuthenticatedUserDetails(source.getSubject(), roles);

        if (roles.isEmpty()) {
            Set<String> defaultAuthorityRole = mapAuthorityRoleName(properties.getDefaultRoles());
            authenticatedUserDetails = authenticatedUserDetails.withAuthorities(defaultAuthorityRole);
        }

        return new UserDetailsAuthenticationToken(authenticatedUserDetails);
    }

    private Set<String> mapAuthorityRoleName(final List<String> values) {
        return values.stream()
                .map(role -> properties.getRolePrefix().toUpperCase() + "_" + role.toUpperCase())
                .collect(Collectors.toSet());
    }

    private Function<Object, List<String>> getClaimsFromJwt() {
        return claimObject -> {
            if (claimObject instanceof Collection<?> collection) {
                return collection.stream().map(Object::toString).collect(Collectors.toList());
            }
            if (claimObject instanceof Claim claim) {
                return claim.asList(String.class);
            }

            throw new IllegalArgumentException("claim object is not recognized");
        };
    }
}
