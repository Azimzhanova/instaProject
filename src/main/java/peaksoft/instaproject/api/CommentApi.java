package peaksoft.instaproject.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.instaproject.dto.SimpleResponse;
import peaksoft.instaproject.dto.commentDTO.request.CommentRequest;
import peaksoft.instaproject.dto.commentDTO.response.CommentResponse;
import peaksoft.instaproject.dto.userDTO.response.UserResponse;
import peaksoft.instaproject.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentApi {
    private final CommentService commentService;

    //create commit
    @PostMapping("/{userId}/{postId}/add-comment")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public CommentResponse createComment(@PathVariable("userId") Long userId,
                                         @PathVariable("postId") Long postId,
                                         @RequestBody CommentRequest commentRequest){
        return commentService.saveComment(userId, postId, commentRequest);
    }

    @GetMapping("{postId}/getComments")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<CommentResponse> getComments(@PathVariable Long postId){
        return commentService.findAllCommentByPostId(postId);
    }

    @DeleteMapping("/{commentId}/delete-comment")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public SimpleResponse deleteCommentById(@PathVariable Long commentId){
        return commentService.deleteCommentById(commentId);
    }
}
