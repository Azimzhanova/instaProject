package peaksoft.instaproject.dto.userDTO.response;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
public record UserResponse(
        String userName,
        @Email(message = "Email должен содержать символ '@' и иметь корректный формат")
        String email,
        String phoneNumber
) {}
