package peaksoft.instaproject.service.impl;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.instaproject.config.jwt.JwtService;
import peaksoft.instaproject.dto.SimpleResponse;
import peaksoft.instaproject.dto.commentDTO.request.CommentRequest;
import peaksoft.instaproject.dto.commentDTO.response.CommentResponse;
import peaksoft.instaproject.entity.Comment;
import peaksoft.instaproject.entity.Like;
import peaksoft.instaproject.entity.Post;
import peaksoft.instaproject.entity.User;
import peaksoft.instaproject.enums.Role;
import peaksoft.instaproject.exception.AccessIsDeniedException;
import peaksoft.instaproject.exception.NotFoundException;
import peaksoft.instaproject.repository.CommentRepository;
import peaksoft.instaproject.repository.LikeRepository;
import peaksoft.instaproject.repository.PostRepository;
import peaksoft.instaproject.repository.UserRepository;
import peaksoft.instaproject.service.CommentService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final JwtService jwtService;

    @Override
    public CommentResponse saveComment(Long userId, Long postId, CommentRequest commentRequest) {
        User currentUser = jwtService.checkToken();
        if(!currentUser.getId().equals(userId)){
            throw new RuntimeException("You can't to comment for this user");
        }

        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("Post not found"));
        Comment comment = new Comment();
        comment.setComment(commentRequest.comment());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUser(currentUser);
        comment.setPost(post);
        commentRepository.save(comment);

        //лайк кошо тузулсун. Бирок лайктын саны 0 болуп турсун
        Like like = new Like();
        like.setUser(currentUser);
        like.setPost(post);
        like.setComment(comment);
        like.setLike(false);
        comment.getLikes().add(like); //add LIKE to collection of comment
        likeRepository.save(like);
        long countLike = comment.getLikes().stream().filter(Like::isLike).count();
        return CommentResponse.builder().comment(comment.getComment()).userName(comment.getUser().getUsername()).postTitle(comment.getPost().getTitle())
                .likeCount((int)countLike).createdAt(comment.getCreatedAt()).build();
    }

    @Override
    public List<CommentResponse> findAllCommentByPostId(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("Post not found"));
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        if(comments.isEmpty()) {
            throw new NotFoundException("comment is not found");
        }
        List<CommentResponse> list = comments.stream().map(c -> {
            long countLike = likeRepository.findAll().stream().filter(Like::isLike).count();
            return CommentResponse.builder().comment(c.getComment())
                    .userName(c.getUser().getUsername()).postTitle(c.getPost().getTitle()).likeCount((int) countLike).createdAt(c.getCreatedAt()).build();
        }).toList();
        return list;
    }


    @Override
    public SimpleResponse deleteCommentById(Long commentId) {
        User currentUser = jwtService.checkToken();

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
