package peaksoft.instaproject.dto.postDTO.response;


import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record PostResponse(
        Long id,
        String title,
        String description,
        String username,
        String imageUrl,
        List<String> collabUsers,
        LocalDateTime createdAt
) { }
