package peaksoft.instaproject.dto.userDTO.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
public record UserResponse(
        Long id,
        String userName,
//        HttpStatus httpStatus,
//        String message,
        String email,
        String phoneNumber
) {}
