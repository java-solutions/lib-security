package pl.javasolutions.security.oauth2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.NullSecurityContextRepository;
import pl.javasolutions.security.SecurityConfigurationProperties;
import pl.javasolutions.security.jwt.SecurityTokenBeanConfiguration;
import pl.javasolutions.security.jwt.TokenService;
import pl.javasolutions.security.user.UserPrincipalBeanConfiguration;
import pl.javasolutions.security.user.UserPrincipalService;

@Slf4j
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
@EnableConfigurationProperties(SecurityConfigurationProperties.class)
@Import({UserPrincipalBeanConfiguration.class, SecurityTokenBeanConfiguration.class})
// FIXME: SpringFacetCodeInspection
class SecurityConfiguration {

    @Bean
    SecurityFilterChain filterChain(
            HttpSecurity http,
            SecurityConfigurationProperties properties,
            TokenService tokenService,
            UserPrincipalService providerUserInfoService,
            Converter<Jwt, JwtAuthenticationToken> jwtAuthenticationTokenConverter
    ) throws Exception {
        log.info("Initialized oauth login security and jwt resource server");
        return http
                .oauth2ResourceServer(resourceServer -> resourceServer
                        .jwt(config -> config
                                .decoder(tokenService)
                                .jwtAuthenticationConverter(jwtAuthenticationTokenConverter)))
                .antMatcher("/**")
                .securityContext(security -> security
                        .securityContextRepository(new NullSecurityContextRepository()))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> requests
                        .antMatchers("/login/oauth2/**").permitAll()
                        .antMatchers("/favicon.ico").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(authenticationEntryPoint()))
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login/oauth2")
                        .successHandler(successHandler(properties))
                        .failureHandler(failureHandler(properties))
                        .authorizationEndpoint(authorization -> authorization
                                .baseUri("/login/oauth2/authorization/**"))
                        .redirectionEndpoint(redirect -> redirect
                                .baseUri("/login/oauth2/callback/*"))
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService(providerUserInfoService))
                                .oidcUserService(oidcUserService(providerUserInfoService))))
                .build();
    }

    private AuthenticationEntryPoint authenticationEntryPoint() {
        return new OAuth2AuthenticationEntryPoint();
    }

    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService(UserPrincipalService userPrincipalService) {
        OidcUserService oidcUserService = new OidcUserService();
        return new OAuth2OidcPrincipalUserService(oidcUserService, userPrincipalService);
    }

    private OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService(UserPrincipalService providerUserInfoService) {
        return new OAuth2PrincipalUserService(providerUserInfoService);
    }

    private AuthenticationSuccessHandler successHandler(SecurityConfigurationProperties properties) {
        DefaultRedirectStrategy defaultRedirectStrategy = new DefaultRedirectStrategy();
        return new OAuth2AuthenticationSuccessHandler(properties.getOauth2(), defaultRedirectStrategy);
    }

    private AuthenticationFailureHandler failureHandler(SecurityConfigurationProperties properties) {
        DefaultRedirectStrategy defaultRedirectStrategy = new DefaultRedirectStrategy();
        return new OAuth2AuthenticationFailureHandler(properties.getOauth2(), defaultRedirectStrategy);
    }
}
