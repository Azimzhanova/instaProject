package peaksoft.instaproject.service;


import peaksoft.instaproject.dto.SimpleResponse;
import peaksoft.instaproject.dto.imageDto.AvatarRequest;
import peaksoft.instaproject.dto.userDTO.response.UserResponse;
import peaksoft.instaproject.dto.userInfoDTO.request.UserInfoRequest;
import peaksoft.instaproject.dto.userInfoDTO.request.UserInfoUpdate;
import peaksoft.instaproject.dto.userInfoDTO.response.UserInfoResponse;

public interface UserInfoService {
    UserInfoResponse saveUserInfo(UserInfoRequest userInfoRequest);
    UserInfoResponse findUserInfoByUserId(Long userId);
    SimpleResponse updateUserInfo(Long userId, UserInfoUpdate userInfoUpdate);
    SimpleResponse changeImage(Long userId, AvatarRequest avatar);
    SimpleResponse deleteImage(Long userId);
}
