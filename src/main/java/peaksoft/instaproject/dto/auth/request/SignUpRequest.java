package peaksoft.instaproject.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import peaksoft.instaproject.enums.Role;
import peaksoft.instaproject.validation.validator.EmailAnnValid;
import peaksoft.instaproject.validation.validator.PasswordAnnValid;
import peaksoft.instaproject.validation.validator.PhoneNumberAnnValid;
import peaksoft.instaproject.validation.validator.UserNameAnnValid;

public record SignUpRequest (
        @NotNull
        @UserNameAnnValid
        String userName,
        @NotNull
        @EmailAnnValid
        String email,
        @NotNull
        @PasswordAnnValid
        String password,
        @NotNull
        @PhoneNumberAnnValid
        String phoneNumber,
        Role role
) {
}
