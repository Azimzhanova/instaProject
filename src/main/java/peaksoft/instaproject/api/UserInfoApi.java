package peaksoft.instaproject.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.instaproject.dto.SimpleResponse;
import peaksoft.instaproject.dto.imageDto.AvatarRequest;
import peaksoft.instaproject.dto.userDTO.response.UserResponse;
import peaksoft.instaproject.dto.userInfoDTO.request.UserInfoRequest;
import peaksoft.instaproject.dto.userInfoDTO.request.UserInfoUpdate;
import peaksoft.instaproject.dto.userInfoDTO.response.UserInfoResponse;
import peaksoft.instaproject.service.UserInfoService;

@RestController
@RequestMapping("/api/userInfos")
@RequiredArgsConstructor
public class UserInfoApi {

    private final UserInfoService userInfoService;

    //todo save info user
    @PostMapping("/add-info")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public UserInfoResponse addUserInfo(@RequestBody UserInfoRequest userInfoRequest) {
        return userInfoService.saveUserInfo(userInfoRequest);
    }

    //todo get user's info by id
    @GetMapping("/{userId}/get-info")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public UserInfoResponse getUserInfo(@PathVariable Long userId) {
        return userInfoService.findUserInfoByUserId(userId);
    }

    //todo update info
    @PutMapping("/{userId}/edit-info")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public SimpleResponse updateInfo(@PathVariable Long userId, @RequestBody UserInfoUpdate infoUpdateRequest) {
        return userInfoService.updateUserInfo(userId, infoUpdateRequest);
    }

    //todo change user's image
    @PutMapping("/{userId}/change-av")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public SimpleResponse updateUserImage(@PathVariable Long userId, @RequestBody AvatarRequest image) {
        return userInfoService.changeImage(userId, image);
    }

    //todo delete user's avatar
    @DeleteMapping("/{userId}/delete-image")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public SimpleResponse deleteUserInfo(@PathVariable Long userId) {
        return userInfoService.deleteImage(userId);
    }
}
