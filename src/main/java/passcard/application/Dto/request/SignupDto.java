package passcard.application.Dto.request;

import jakarta.validation.constraints.NotNull;

public record SignupDto(

    @NotNull(message = "username can be null but not blank")
    String username,

    @NotNull(message = "Registration email can be null but not blank")
    String email,

    @NotNull(message = "Registration password cannot be null")
    String password
){}
