package peaksoft.instaproject.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.instaproject.config.jwt.JwtService;
import peaksoft.instaproject.dto.SimpleResponse;
import peaksoft.instaproject.dto.postDTO.response.PostResponse;
import peaksoft.instaproject.dto.userDTO.request.UserUpdateRequest;
import peaksoft.instaproject.dto.userDTO.response.UserByIdResponse;
import peaksoft.instaproject.dto.userDTO.response.UserProfileResponse;
import peaksoft.instaproject.dto.userDTO.response.UserResponse;
import peaksoft.instaproject.dto.userDTO.response.UserUpdateResponse;
import peaksoft.instaproject.entity.Follower;
import peaksoft.instaproject.entity.Image;
import peaksoft.instaproject.entity.User;
import peaksoft.instaproject.exception.AccessIsDeniedException;
import peaksoft.instaproject.repository.FollowerRepository;
import peaksoft.instaproject.repository.UserRepository;
import peaksoft.instaproject.service.UserService;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final FollowerRepository followerRepository;

    @Override
    public UserByIdResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("User with id " + id + " not found"));
        return UserByIdResponse.builder()
                .userName(user.getUsername())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.getAllUsers()
                .stream()
                .map(u -> new UserResponse(u.userName(), u.email(), u.phoneNumber()))
                .toList();
    }

    @Override
    public UserUpdateResponse updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        User currentUser = jwtService.checkToken();
        User oldUser = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with such " + id + " not found"));

        if (!currentUser.getEmail().equals(oldUser.getEmail())) {
            throw new AccessIsDeniedException("You can't update this user");
        }

        oldUser.setUserName(userUpdateRequest.userName());
        oldUser.setEmail(userUpdateRequest.email());
        oldUser.setPhoneNumber(userUpdateRequest.phoneNumber());

        userRepository.save(oldUser);

        if (oldUser.getFollower() == null) {
            Follower follower = new Follower();
            follower.setUser(oldUser);
            followerRepository.save(follower);
        }

        String token = jwtService.generateToken(oldUser);

        return UserUpdateResponse.builder()
                .status(HttpStatus.OK)
                .message("User is updated")
                .token(token)
                .build();
    }

    @Override
    public SimpleResponse deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("User with id " + id + " not found");
        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("User with id %s is deleted", id))
                .build();
    }

    @Override
    public UserProfileResponse userProfile(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("User with id " + id + " not found"));

        int countSubscriptions = user.getFollower() == null ? 0 : user.getFollower().getSubscriptions().size();
        int countSubscribers = user.getFollower() == null ? 0 : user.getFollower().getSubscribers().size();

        List<PostResponse> postList = user.getPosts()
                .stream()
                .map(p -> PostResponse.builder()
                        .id(p.getId())
                        .title(p.getTitle())
                        .description(p.getDescription())
                        .imageUrl(String.valueOf(p.getImages().stream().map(Image::getImageUrl).toList()))
                        .collabUsers(p.getCollabs().stream().map(User::getUsername).toList())
                        .createdAt(p.getCreatedAt())
                        .build())
                .sorted(Comparator.comparing(PostResponse::createdAt).reversed())
                .toList();

        String fullName = user.getUserInfo() != null && user.getUserInfo().getFullName() != null ?
                user.getUserInfo().getFullName() : "";

        String imageUrl = user.getUserInfo() != null && user.getUserInfo().getImage() != null ?
                user.getUserInfo().getImage() : null;

        return UserProfileResponse.builder()
                .username(user.getUsername())
                .fullName(fullName)
                .image(imageUrl)
                .subscribers(countSubscribers)
                .subscriptions(countSubscriptions)
                .posts(postList)
                .build();
    }
}


