package peaksoft.instaproject.dto.userDTO.request;

import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        String fullName,
        String userName,
        String password,
        String email,
        String phoneNumber
) { }
