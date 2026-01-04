package peaksoft.instaproject.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import peaksoft.instaproject.config.jwt.JwtService;
import peaksoft.instaproject.dto.SimpleResponse;
import peaksoft.instaproject.dto.userDTO.response.UserResponse;
import peaksoft.instaproject.entity.Comment;
import peaksoft.instaproject.entity.Like;
import peaksoft.instaproject.entity.Post;
import peaksoft.instaproject.entity.User;
import peaksoft.instaproject.exception.NotFoundException;
import peaksoft.instaproject.repository.CommentRepository;
import peaksoft.instaproject.repository.LikeRepository;
import peaksoft.instaproject.repository.PostRepository;
import peaksoft.instaproject.repository.UserRepository;
import peaksoft.instaproject.service.LikeService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final JwtService jwtService;
    private final CommentRepository commentRepository;

    @Override
    public SimpleResponse likeForPost(Long userId, Long postId) {

        User currentUser = jwtService.checkToken();

        if (!currentUser.getId().equals(userId)) {
            throw new BadCredentialsException("You are not allowed to like this post!");
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found"));

        List<Like> likes = likeRepository.findByUserIdAndPostId(userId, postId);

        if (!likes.isEmpty()) {
            likeRepository.deleteAll(likes);
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Like removed")
                    .build();
        }

        Like like = new Like();
        like.setUser(currentUser);
        like.setPost(post);
        like.setLike(true);

        likeRepository.save(like);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Like added")
                .build();
    }

    @Override
    public SimpleResponse likeForComment(Long userId, Long commentId) {

        User currentUser = jwtService.checkToken();

        if (!currentUser.getId().equals(userId)) {
            throw new BadCredentialsException("You are not allowed to like this comment!");
        }

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Comment not found"));

        List<Like> likes = likeRepository.findByUserIdAndCommentId(userId, commentId);

        if (!likes.isEmpty()) {
            likeRepository.deleteAll(likes);
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Like removed")
                    .build();
        }

        Like like = new Like();
        like.setUser(currentUser);
        like.setComment(comment);
        like.setLike(true);

        likeRepository.save(like);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Like added")
                .build();
    }
}
