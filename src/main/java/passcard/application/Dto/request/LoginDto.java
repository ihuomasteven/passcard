package passcard.application.Dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDto(
    @Size(min = 3, max = 30)
    @NotBlank(message = "Please fill in your username or email")
    String username,

    @NotBlank(message = "Login password cannot be blank")
    String password
){}
