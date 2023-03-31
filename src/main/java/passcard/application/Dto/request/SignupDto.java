package passcard.application.Dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import passcard.domain.enums.Role;

import java.io.Serializable;
import java.util.Set;

public record SignupDto(

    @NotNull(message = "Username cannot be blank")
    String username,

    @Email
    String email,

    @NotNull(message = "Password cannot be blank")
    String password,

    Set<Role> roles
) implements Serializable {}
