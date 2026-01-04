package peaksoft.instaproject.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import peaksoft.instaproject.validation.validator.PasswordAnnValid;

public class PasswordValidation implements ConstraintValidator<PasswordAnnValid,String> {
    @Override
    public void initialize(PasswordAnnValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if(password == null) return true;
        return password.length() >= 6 && containUpperCase(password);
    }

    private boolean containUpperCase(String password) {
        return password.matches(".*[A-Z].*");
    }
}
