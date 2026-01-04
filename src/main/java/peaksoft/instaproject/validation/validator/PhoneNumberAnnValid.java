package peaksoft.instaproject.validation.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import peaksoft.instaproject.validation.PhoneNumberValidation;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {PhoneNumberValidation.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumberAnnValid {
    String message() default "phone number must start with '+996'";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
