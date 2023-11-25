package passcard.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import passcard.role.Role;
import passcard.role.RoleRepository;
import passcard.role.AppRole;

import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminRunner implements CommandLineRunner {

    @Value("${app.admin.username}")
    private String USERNAME;

    @Value("${app.admin.email}")
    private String EMAIL;

    @Value("${app.admin.password}")
    private String PASSWORD;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Role role = roleRepository.findByName(AppRole.ADMIN);
        boolean existsAdmin = userRepository.existsByRoles(role);

        if (!existsAdmin) {
            User adminUser = User.builder()
                    .username(USERNAME)
                    .email(EMAIL)
                    .roles(Collections.singleton(role))
                    .isEmailVerified(true)
                    .password(passwordEncoder.encode(PASSWORD))
                    .build();

            userRepository.save(adminUser);
            log.info("User with ADMIN role created successfully");
        }
    }
}
