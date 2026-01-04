package peaksoft.instaproject.dto.auth.response;

import lombok.Builder;
import peaksoft.instaproject.enums.Role;

@Builder
public record AuthResponse (
    Long id,
    String token,
    Role role
){}
