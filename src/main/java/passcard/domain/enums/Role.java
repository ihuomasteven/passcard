package passcard.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Role {
    USER,
    ADMIN,
    SYSTEM
}


//public enum Role {
//    USER("USER"),
//    ADMIN("ADMIN"),
//    SYSTEM("SYSTEM");
//
//    private final String jsonValue;
//
//    private Role(final String json) {
//        this.jsonValue = json;
//    }
//
//    @JsonValue
//    public String jsonValue() {
//        return this.jsonValue;
//    }
//}
