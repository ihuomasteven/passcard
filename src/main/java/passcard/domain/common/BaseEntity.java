package passcard.domain.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;

import java.io.Serial;
import java.io.Serializable;

@Data
public class BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 5L;

    @Id
    private Long id;

    @JsonIgnore
    @CreatedBy
    @Column("created_by")
    private String createdBy;

    @JsonIgnore
    @CreatedDate
    @Column("created_dt")
    private String createdDate;

    @JsonIgnore
    @LastModifiedBy
    @Column("updated_by")
    private String updatedBy;

    @JsonIgnore
    @LastModifiedDate
    @Column("updated_dt")
    private String updatedDate;

    @JsonIgnore
    @Version
    private Integer version;
}
