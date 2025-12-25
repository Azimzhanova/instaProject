package peaksoft.instaproject.dto.commentDTO.request;

import jakarta.validation.constraints.NotBlank;

public record CommentRequest(
        String comment
) { }
