package passcard.role;

import org.springframework.data.jpa.repository.JpaRepository;
import passcard.shared.enums.AppRole;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByName(AppRole role);
//    boolean existsRole();
}
