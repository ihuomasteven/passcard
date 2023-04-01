package passcard.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import passcard.domain.entity.User;
import passcard.infrastructure.repository.UserRepository;
import passcard.infrastructure.security.AuthEntryPoint;
import passcard.infrastructure.security.AuthFilter;
import passcard.infrastructure.security.AuthenticationManager;
import passcard.infrastructure.security.SecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    protected static final String[] STATIC_WHITELIST = {
            "/js/**",
            "/css/**",
            "/images/**",
            "/public/**",
            "/webjars/**",
            "/favicon.ico",
            "/h2-console/**",
            "/actuator/info",
            "/actuator/health"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService(UserRepository repository) {

        return username -> repository.findByUsername(username)
                .map(user -> User.builder()
                        .password(passwordEncoder().encode(user.getPassword()))
                        .username(user.getUsername())
                        .roles(user.getRoles())
                        .build()
                );
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
                .logout().disable()
                .httpBasic().disable()
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange(auth -> auth
                        .pathMatchers(HttpMethod.POST, "/api/user/*").permitAll()
                        .pathMatchers(HttpMethod.GET, STATIC_WHITELIST).permitAll()
                        .anyExchange().authenticated()
                )
                .addFilterAt(authFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}
