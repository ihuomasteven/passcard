package passcard.shared.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import passcard.shared.validation.annotation.MatchPassword;
import passcard.user.dtos.ResetPasswordDto;


public class MatchPasswordValidator implements ConstraintValidator<MatchPassword, ResetPasswordDto> {

    private Boolean allowNull;

    @Override
    public void initialize(MatchPassword constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(ResetPasswordDto value, ConstraintValidatorContext context) {
        String password = value.password();
        String confirmPassword = value.confirmPassword();
        if (allowNull) {
            return null == password && null == confirmPassword;
        }
        return password.equals(confirmPassword);
    }
}
