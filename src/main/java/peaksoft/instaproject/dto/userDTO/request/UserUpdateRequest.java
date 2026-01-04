package peaksoft.instaproject.dto.userDTO.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import peaksoft.instaproject.validation.validator.PhoneNumberAnnValid;

import java.awt.*;

public record UserUpdateRequest(
        String userName,
        String email,
        @NotNull
        @PhoneNumberAnnValid
        String phoneNumber
) { }
