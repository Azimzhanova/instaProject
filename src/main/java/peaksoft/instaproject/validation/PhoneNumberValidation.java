package peaksoft.instaproject.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import peaksoft.instaproject.validation.validator.PhoneNumberAnnValid;

public class PhoneNumberValidation implements ConstraintValidator<PhoneNumberAnnValid, String> {
    //option 2
    //private static final String PHONE_REGEX = "^\\+996\\d+$";
    //^ - начало строки
    // \\+996 - точное совпадение с символом +996
    // \\d+ - одна или более цифр (от 0 до 9)
    // $ - конец строки
    //private Pattern pattern;
    @Override
    public void initialize(PhoneNumberAnnValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(String number, ConstraintValidatorContext constraintValidatorContext) {
        if(number==null){ return true;}
        return number.matches("^\\+996\\d{9}$");
    }
}
