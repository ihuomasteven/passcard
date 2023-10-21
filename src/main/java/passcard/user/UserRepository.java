package passcard.user;

import org.springframework.data.jpa.repository.JpaRepository;
import passcard.role.Role;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByRoles(Role role);
}
