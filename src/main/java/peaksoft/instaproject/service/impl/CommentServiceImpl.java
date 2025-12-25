package peaksoft.instaproject.service.impl;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.instaproject.config.jwt.JwtService;
import peaksoft.instaproject.dto.SimpleResponse;
import peaksoft.instaproject.dto.commentDTO.request.CommentRequest;
import peaksoft.instaproject.dto.commentDTO.response.CommentResponse;
import peaksoft.instaproject.entity.Comment;
import peaksoft.instaproject.entity.Post;
import peaksoft.instaproject.entity.User;
import peaksoft.instaproject.enums.Role;
import peaksoft.instaproject.exception.AccessIsDeniedException;
import peaksoft.instaproject.exception.NotFoundException;
import peaksoft.instaproject.repository.CommentRepository;
import peaksoft.instaproject.repository.LikeRepository;
import peaksoft.instaproject.repository.PostRepository;
import peaksoft.instaproject.service.CommentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final JwtService jwtService;


    @Override
    public CommentResponse createComment(Long postId, CommentRequest commentRequest) {

        User currentUser = jwtService.checkAuthentication();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Post not found"));

        Comment comment = new Comment();
        comment.setComment(commentRequest.comment());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUser(currentUser);
        comment.setPost(post);

        commentRepository.save(comment);

        return CommentResponse.builder()
                .comment(comment.getComment())
                .userName(currentUser.getUsername())
                .postTitle(post.getTitle())
                .likeCount(0)
                .createdAt(comment.getCreatedAt())
                .build();
    }

    @Override
    public CommentResponse saveComment(Long userId, Long postId, CommentRequest commentRequest) {return null;}

    @Override
    public List<CommentResponse> findAllCommentByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new NoSuchElementException("Post not fount!"));

        List<Comment> comments = commentRepository.findAllByPostId(postId);

        if (comments.isEmpty()){
            throw new NotFoundException("Comments not found");
        }
        return comments.stream().map(comment -> {
            long likeCount
                    = likeRepository.countByCommentIdAndLikeTrue(comment.getId());

            return CommentResponse.builder()
                    .comment(comment.getComment())
                    .userName(comment.getUser().getUsername())
                    .postTitle(post.getTitle())
                    .likeCount((int) likeCount)
                    .createdAt(comment.getCreatedAt())
                    .build();
        }).toList();
        }


    @Override
    public SimpleResponse deleteCommentById(Long commentId) {
        User currentUser = jwtService.checkAuthentication();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Comment not found"));

        if (!comment.getUser().getId().equals(currentUser.getId())
                && currentUser.getRole() != Role.ADMIN) {
            throw new AccessIsDeniedException("Comment not found!");
        }

        commentRepository.delete(comment);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Comment removed successfully")
                .build();
    }
}
