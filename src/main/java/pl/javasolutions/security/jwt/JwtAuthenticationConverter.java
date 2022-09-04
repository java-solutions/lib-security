package pl.javasolutions.security.jwt;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

class JwtAuthenticationConverter implements Converter<Jwt, JwtAuthenticationToken> {

    private static final List<GrantedAuthority> DEFAULT_GRANTED_AUTHORITY = List.of(new SimpleGrantedAuthority("ROLE_DEMO"));

    @Override
    public JwtAuthenticationToken convert(final Jwt source) {
        List<String> roles = source.getClaimAsStringList(TokenService.CLAIMS_ROLES_KEY);

        if (nonNull(roles)) {

            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            return new JwtAuthenticationToken(source, authorities, source.getSubject());
        }

        return new JwtAuthenticationToken(source, DEFAULT_GRANTED_AUTHORITY, source.getSubject());
    }
}
