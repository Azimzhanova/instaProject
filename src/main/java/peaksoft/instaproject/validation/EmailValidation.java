package peaksoft.instaproject.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import peaksoft.instaproject.validation.validator.EmailAnnValid;

public class EmailValidation implements ConstraintValidator<EmailAnnValid, String> {
    @Override
    public void initialize(EmailAnnValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email == null) { return true; }
        return email.toLowerCase().endsWith("@gmail.com");
    }
}
