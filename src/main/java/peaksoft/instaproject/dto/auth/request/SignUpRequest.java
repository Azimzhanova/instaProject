package peaksoft.instaproject.dto.auth.request;

import peaksoft.instaproject.enums.Role;

public record SignUpRequest (
        String userName,
        String email,
        String password,
        String phoneNumber,
        Role role
) {
}
