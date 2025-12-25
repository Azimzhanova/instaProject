package peaksoft.instaproject.dto.commentDTO.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentResponse(
        String comment,
        LocalDateTime createdAt,
        String userName,
        String postTitle,
        int likeCount
) { }
