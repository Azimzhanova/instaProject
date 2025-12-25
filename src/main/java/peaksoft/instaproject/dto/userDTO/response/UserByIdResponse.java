package peaksoft.instaproject.dto.userDTO.response;

import lombok.Builder;

@Builder
public record UserByIdResponse(
        String userName,
        String email,
        String phoneNumber
) { }
