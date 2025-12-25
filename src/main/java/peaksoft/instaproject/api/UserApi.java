package peaksoft.instaproject.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.instaproject.dto.SimpleResponse;
import peaksoft.instaproject.dto.userDTO.request.UserUpdateRequest;
import peaksoft.instaproject.dto.userDTO.response.UserByIdResponse;
import peaksoft.instaproject.dto.userDTO.response.UserProfileResponse;
import peaksoft.instaproject.dto.userDTO.response.UserResponse;
import peaksoft.instaproject.dto.userDTO.response.UserUpdateResponse;
import peaksoft.instaproject.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

    //todo get user by id
    @GetMapping("/{id}")
    public UserByIdResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    //todo get all users
    @GetMapping("/all-users")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    //todo update user by id
    @PutMapping("/{id}/update")
    public UserUpdateResponse updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest userUpdateRequest) {
        return userService.updateUser(id, userUpdateRequest);
    }

    //todo delete user by id
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public SimpleResponse deleteUser(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }

    //todo get user's Profile
    @GetMapping("/{id}/user-profile")
    public UserProfileResponse getProfile(@PathVariable Long id) {
        return userService.userProfile(id);
    }
}
