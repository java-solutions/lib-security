package pl.javasolutions.security.jwt;

import com.auth0.jwt.interfaces.Claim;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import pl.javasolutions.security.SecurityConfigurationProperties.TokenProperties;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PROTECTED;
import static pl.javasolutions.security.jwt.TokenService.CLAIMS_ROLES_KEY;

@RequiredArgsConstructor(access = PROTECTED)
class JwtAuthenticationConverter implements Converter<Jwt, JwtAuthenticationToken> {

    private final TokenProperties properties;

    @Override
    public JwtAuthenticationToken convert(final Jwt source) {

        List<String> roles = Optional.ofNullable(source.getClaim(CLAIMS_ROLES_KEY))
                .map(getClaimsFromJwt())
                .orElseGet(List::of);

        if (!roles.isEmpty()) {
            List<SimpleGrantedAuthority> authorities = getAuthorities(roles);
            return new JwtAuthenticationToken(source, authorities);
        }

        return new JwtAuthenticationToken(source, getAuthorities(properties.getDefaultRoles()));
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

    private List<SimpleGrantedAuthority> getAuthorities(List<String> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(properties.getRolePrefix().toUpperCase() + "_" + role.toUpperCase()))
                .collect(Collectors.toList());
    }

}
