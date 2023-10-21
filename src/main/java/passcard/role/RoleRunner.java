package passcard.role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import passcard.shared.enums.AppRole;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoleRunner implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        boolean existsRoles = roleRepository.findAll().isEmpty();

        if (existsRoles) {
            for (AppRole appRole : AppRole.values()) {
                Role role = new Role();
                role.setName(appRole);
                role.setDescription("");
                roleRepository.save(role);
            }
            log.info("Roles added successfully");
        }
    }
}
