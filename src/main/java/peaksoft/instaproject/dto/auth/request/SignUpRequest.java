package peaksoft.instaproject.dto.auth.request;

import jakarta.validation.constraints.NotNull;
import peaksoft.instaproject.enums.Role;

public record SignUpRequest (
        @NotNull
        String userName,
        @NotNull
        String email,
        @NotNull
        String password,
        @NotNull
        String phoneNumber,
        Role role
) {
}
