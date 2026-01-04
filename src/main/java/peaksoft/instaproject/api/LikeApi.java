package peaksoft.instaproject.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.instaproject.dto.SimpleResponse;
import peaksoft.instaproject.dto.userDTO.response.UserResponse;
import peaksoft.instaproject.service.LikeService;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class  LikeApi {
    private final LikeService likeService;

    //todo Like for Post
    @PostMapping("/users/{userId}/posts/{postId}/post-like")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public SimpleResponse addLikeToPost(@PathVariable Long userId, @PathVariable Long postId) {
        return likeService.likeForPost(userId, postId);
    }

    //todo Like for Comment
    @PostMapping("/users/{userId}/comments/{commentId}/comment-like")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public SimpleResponse addLikeToComment(@PathVariable("userId") Long userId, @PathVariable("commentId") Long commentId) {
        return likeService.likeForComment(userId, commentId);
    }
}
