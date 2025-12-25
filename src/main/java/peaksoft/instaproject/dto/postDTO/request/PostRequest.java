package peaksoft.instaproject.dto.postDTO.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record PostRequest(
       String title,
       String description,
       String imageUrl,
       List<Long> collabsUserId
) { }
