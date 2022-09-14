package pl.javasolutions.security.jwt;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Import;
import pl.javasolutions.security.config.TestClockConfiguration;
import pl.javasolutions.security.config.TestPropertiesConfiguration;

@SpringBootConfiguration
@Import({TestClockConfiguration.class, SecurityTokenBeanConfiguration.class, TestPropertiesConfiguration.class})
class JwtTestConfiguration {


}
