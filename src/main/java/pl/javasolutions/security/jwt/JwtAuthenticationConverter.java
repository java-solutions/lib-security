package pl.javasolutions.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import pl.javasolutions.security.SecurityConfigurationProperties.TokenProperties;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PROTECTED;
import static pl.javasolutions.security.jwt.TokenService.CLAIMS_ROLES_KEY;

@RequiredArgsConstructor(access = PROTECTED)
class JwtAuthenticationConverter implements Converter<Jwt, JwtAuthenticationToken> {

    private final TokenProperties properties;

    @Override
    public JwtAuthenticationToken convert(final Jwt source) {
        List<String> roles = source.getClaimAsStringList(CLAIMS_ROLES_KEY);

        if (nonNull(roles)) {
            List<SimpleGrantedAuthority> authorities = getAuthorities(roles);
            return new JwtAuthenticationToken(source, authorities);
        }

        return new JwtAuthenticationToken(source, getAuthorities(properties.getDefaultRoles()));
    }

    private List<SimpleGrantedAuthority> getAuthorities(List<String> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(properties.getRolePrefix().toUpperCase() + "_" + role.toUpperCase()))
                .collect(Collectors.toList());
    }

}
