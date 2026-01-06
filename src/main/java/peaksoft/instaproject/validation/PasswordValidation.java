package peaksoft.instaproject.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import peaksoft.instaproject.validation.validator.PasswordAnnValid;

public class PasswordValidation implements ConstraintValidator<PasswordAnnValid,String> {
    @Override
    public void initialize(PasswordAnnValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || password.isEmpty()) {
            return true;
        }
        return password.length() >= 6;
    }
}
