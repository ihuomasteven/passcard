package passcard.application.Dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Please fill in your username or email")
    private String username;

    @NotNull(message = "Login password cannot be blank")
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
