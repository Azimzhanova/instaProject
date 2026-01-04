package peaksoft.instaproject.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.instaproject.config.jwt.JwtService;
import peaksoft.instaproject.dto.SimpleResponse;
import peaksoft.instaproject.dto.postDTO.request.PostRequest;
import peaksoft.instaproject.dto.postDTO.response.PostResponse;
import peaksoft.instaproject.dto.userDTO.response.UserResponse;
import peaksoft.instaproject.entity.Image;
import peaksoft.instaproject.entity.Like;
import peaksoft.instaproject.entity.Post;
import peaksoft.instaproject.entity.User;
import peaksoft.instaproject.enums.Role;
import peaksoft.instaproject.exception.AccessIsDeniedException;
import peaksoft.instaproject.exception.NotFoundException;
import peaksoft.instaproject.repository.LikeRepository;
import peaksoft.instaproject.repository.PostRepository;
import peaksoft.instaproject.repository.UserRepository;
import peaksoft.instaproject.service.PostService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    @Override
    public PostResponse createPost(Long userId, PostRequest postRequest) {
        User currentUser = jwtService.checkToken();
        if (!currentUser.getId().equals(userId)) {
            throw new AccessIsDeniedException("You are not allowed to create post!");
        }
        Post post = new Post();
        if (postRequest.imageUrl() == null || postRequest.imageUrl().isBlank()) {
            throw new NotFoundException("the post must have image to create post");
        }
        post.setTitle(postRequest.title());
        post.setDescription(postRequest.description());
        post.setCreatedAt(LocalDateTime.now());
        post.setUser(currentUser);
        if (postRequest.collabsUserId() != null && !postRequest.collabsUserId().isEmpty()) {
            List<User> userCollabs = userRepository.findAllById(postRequest.collabsUserId());
            post.setCollabs(userCollabs);
        }
        Image image = new Image();
        image.setImageUrl(postRequest.imageUrl());
        image.setPost(post);
        post.getImages().add(image);
        postRepository.save(post);
        Like like = new Like();
        like.setLike(false);
        like.setPost(post);
        like.setUser(currentUser);
        likeRepository.save(like);
        return PostResponse
                .builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .username(currentUser.getUsername())
                .collabUsers(post.getCollabs()
                        .stream().map(User::getUsername).toList()).imageUrl(image.getImageUrl())
                .createdAt(post.getCreatedAt()).build();
    }

    @Override
    public PostResponse updatePost(Long postId, PostRequest postRequest) {
        User currentUser = jwtService.checkToken();
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new NotFoundException("post not found"));
        if (!post.getUser().getId().equals(currentUser.getId())) {
            throw new AccessIsDeniedException("You are not allowed to update post!");
        }
        post.setTitle(postRequest.title());
        post.setDescription(postRequest.description());
        postRepository.save(post);
        return PostResponse
                .builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .username(post.getUser().getUsername())
                .collabUsers(post.getCollabs().stream().map(User::getUsername).
                        toList()).imageUrl(post.getImages().get(0).getImageUrl())
                .createdAt(post.getCreatedAt())
                .build();
    }

    @Override
    public List<PostResponse> instFeed(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        List<Long> subscriptionsId = new ArrayList<>();
        //проверяем есть ли followers
        if (user.getFollower() != null) {
            subscriptionsId.addAll(user.getFollower().getSubscriptions().stream().map(User::getId).toList());
        }
        subscriptionsId.add(user.getId()); //жана озунун посту
        if (subscriptionsId.isEmpty()) {
            return Collections.emptyList();
        }

        List<Post> postList = postRepository.findAllByIdLikeDescending(subscriptionsId);
        return postList.stream().map(p -> PostResponse.builder().id(p.getId()).title(p.getTitle()).description(p.getDescription()).username(p.getUser().getUsername())
                .imageUrl(p.getImages().stream().findFirst().map(Image::getImageUrl).orElse(null)).collabUsers(p.getCollabs().stream().map(User::getUsername).toList())
                .createdAt(p.getCreatedAt()).build()).toList();
    }


    @Override
    public SimpleResponse deletePost(Long userId, Long postId) {
     // Получаем текущего user'a
        User currentUser = jwtService.checkToken();

        // Ищем post
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found"));

        // Проверка прав: User или admin
        if (!post.getUser().getId().equals(currentUser.getId()) && currentUser.getRole() != Role.ADMIN) {
            throw new AccessIsDeniedException("You are not allowed to delete this post!");
        }

        post.getUser().getPosts().removeIf(p -> p.getId().equals(postId));
        postRepository.delete(post);

        // Возвращаем ответ
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Post successfully deleted")
                .build();
    }



}
