package passcard.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import passcard.shared.entity.BaseEntity;
import passcard.user.User;

import java.util.Collection;

@Data
@Builder
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "role")
@Table(name = "roles")
@EqualsAndHashCode(callSuper = false)
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private AppRole name;

    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;
}
