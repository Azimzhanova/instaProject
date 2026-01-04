package peaksoft.instaproject.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import peaksoft.instaproject.validation.validator.UserNameAnnValid;

public class UserNameValidation implements ConstraintValidator<UserNameAnnValid, String> {
    @Override
    public void initialize(UserNameAnnValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if(username == null) return true; //null не проверяем - это задача @NotNull
        return !username.trim().isEmpty();
    }
}
