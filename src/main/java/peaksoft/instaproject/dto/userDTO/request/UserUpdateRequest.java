package peaksoft.instaproject.dto.userDTO.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.awt.*;

public record UserUpdateRequest(
        String userName,
        String email,
        String password,
        String phoneNumber
) { }
