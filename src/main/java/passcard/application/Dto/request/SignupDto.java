package passcard.application.Dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import passcard.domain.enums.Role;

import java.io.Serializable;
import java.util.Set;

public record SignupDto(
    @NotBlank(message = "Username cannot be blank")
    String username,

    @Email
    String email,

    @NotBlank(message = "Password cannot be blank")
    String password,

    @NotBlank(message = "Role must note be blank")
    Set<Role> roles
) implements Serializable {}
