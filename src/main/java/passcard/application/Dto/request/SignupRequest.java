package passcard.application.Dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @NotNull(message = "username can be null but not blank")
    private String userName;

    @NotNull(message = "Registration email can be null but not blank")
    private String email;

    @NotNull(message = "Registration password cannot be null")
    private String password;

}
