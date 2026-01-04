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
        //ConstraintValidator.super.initialize(constraintAnnotation);
        //this.pattern = Pattern.compile(PHONE_REGEX); //option 2
    }

    @Override
    public boolean isValid(String number, ConstraintValidatorContext constraintValidatorContext) {
        if(number==null){ return true;}
        //return number.startsWith("+996");
        //option 2
        /*Matcher matcher = pattern.matcher(number);
        return matcher.matches();*/
        //option 3
//        boolean st = number.startsWith("+996");
//        boolean l = number.length() == 9;
//        boolean result = number.substring(4).matches("\\d+");
//        return st && l && result;
        return number.matches("^\\+996\\d{9}$");
    }
}
