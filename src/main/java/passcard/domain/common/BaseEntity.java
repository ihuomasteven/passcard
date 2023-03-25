package passcard.domain.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.*;

import java.io.Serial;
import java.io.Serializable;

public class BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 5L;

    @Id
    private Long id;

    @JsonIgnore
    @CreatedBy
    private String createdBy;

    @JsonIgnore
    @CreatedDate
    private String createdDate;

    @JsonIgnore
    @LastModifiedBy
    private String updatedBy;

    @JsonIgnore
    @LastModifiedDate
    private String updatedDate;

    @JsonIgnore
    @Version
    private Integer version = 0;
}
