package pl.javasolutions.security.user;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import pl.javasolutions.security.jwt.SecurityTokenBeanConfiguration;
import pl.javasolutions.security.jwt.TokenService;

@Import(SecurityTokenBeanConfiguration.class)
public class UserPrincipalBeanConfiguration {

    @Bean
    UserPrincipalService userPrincipalService(TokenService tokenService, UserRepository userRepository) {
        return new UserPrincipalServiceImpl(tokenService, userRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    UserRepository defaultUserRepository() {
        return new DefaultUserRepository();
    }
}
