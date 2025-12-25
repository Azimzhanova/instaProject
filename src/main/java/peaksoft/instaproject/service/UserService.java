package peaksoft.instaproject.service;


import peaksoft.instaproject.dto.SimpleResponse;
import peaksoft.instaproject.dto.userDTO.request.UserUpdateRequest;
import peaksoft.instaproject.dto.userDTO.response.UserByIdResponse;
import peaksoft.instaproject.dto.userDTO.response.UserProfileResponse;
import peaksoft.instaproject.dto.userDTO.response.UserResponse;
import peaksoft.instaproject.dto.userDTO.response.UserUpdateResponse;

import java.util.List;

public interface UserService {
    UserByIdResponse getUserById(Long id);
    List<UserResponse> getAllUsers();
    UserUpdateResponse updateUser (Long ud, UserUpdateRequest userUpdateRequest);
    SimpleResponse deleteUserById(Long id);
    UserProfileResponse userProfile(Long id);
}
