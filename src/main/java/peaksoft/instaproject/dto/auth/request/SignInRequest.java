package peaksoft.instaproject.dto.auth.request;

import jakarta.validation.constraints.NotNull;
import peaksoft.instaproject.validation.validator.EmailAnnValid;

public record SignInRequest (
        @NotNull
        @EmailAnnValid
        String email,
        @NotNull
        String password
) {
}
