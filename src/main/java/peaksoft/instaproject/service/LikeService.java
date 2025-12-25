package peaksoft.instaproject.service;

import peaksoft.instaproject.dto.SimpleResponse;
import peaksoft.instaproject.dto.userDTO.response.UserResponse;

public interface LikeService {
    SimpleResponse likeForPost(Long userId, Long postId);
    SimpleResponse likeForComment(Long userId, Long commentId);
}
