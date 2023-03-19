package passcard.infrastructure.config;

import passcard.infrastructure.security.AuthEntryPoint;
import passcard.infrastructure.security.AuthFilter;
import passcard.infrastructure.security.AuthenticationManager;
import passcard.infrastructure.security.SecurityContextRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http,
            AuthFilter authFilter,
            AuthEntryPoint authEntryPoint,
            AuthenticationManager authenticationManager,
            SecurityContextRepository securityContextRepository
    ) {
        return http
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint)
                .and()
                .csrf().disable()
                .httpBasic().disable()
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange(auth -> auth
                        .pathMatchers(HttpMethod.POST, "/api/user/*").permitAll()
                        .anyExchange().authenticated()
                )
                .addFilterAt(authFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}
