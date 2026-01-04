package peaksoft.instaproject.service;

import peaksoft.instaproject.dto.SimpleResponse;
import peaksoft.instaproject.dto.commentDTO.request.CommentRequest;
import peaksoft.instaproject.dto.commentDTO.response.CommentResponse;
import peaksoft.instaproject.dto.userDTO.response.UserResponse;

import java.util.List;

public interface CommentService {

    CommentResponse saveComment(Long userId, Long postId, CommentRequest commentRequest);
    List<CommentResponse> findAllCommentByPostId(Long postId);
    SimpleResponse deleteCommentById(Long commentId);
}
