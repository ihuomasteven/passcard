package passcard.user;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum AccountStatus {
    ENABLED,
    DISABLED,
    ACTIVE
}
