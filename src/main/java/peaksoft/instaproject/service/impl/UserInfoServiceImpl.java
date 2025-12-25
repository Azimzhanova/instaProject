package peaksoft.instaproject.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import peaksoft.instaproject.config.jwt.JwtService;
import peaksoft.instaproject.dto.SimpleResponse;
import peaksoft.instaproject.dto.imageDto.AvatarRequest;
import peaksoft.instaproject.dto.userDTO.response.UserResponse;
import peaksoft.instaproject.dto.userInfoDTO.request.UserInfoRequest;
import peaksoft.instaproject.dto.userInfoDTO.request.UserInfoUpdate;
import peaksoft.instaproject.dto.userInfoDTO.response.UserInfoResponse;
import peaksoft.instaproject.entity.User;
import peaksoft.instaproject.entity.UserInfo;
import peaksoft.instaproject.repository.UserInfoRepository;
import peaksoft.instaproject.repository.UserRepository;
import peaksoft.instaproject.service.UserInfoService;

@Service
@Transactional
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoRepository userInfoRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public UserInfoResponse saveUserInfo(UserInfoRequest userInfoRequest) {
        User currentUser = jwtService.checkAuthentication();
        if (currentUser.getUserInfo() != null) {
            throw new RuntimeException("Info about User already exists");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setFullName(userInfoRequest.fullName());
        userInfo.setBiography(userInfoRequest.biography());
        userInfo.setGender(userInfoRequest.gender());
        userInfo.setImage(userInfoRequest.image());
        userInfo.setUser(currentUser);
        currentUser.setUserInfo(userInfo);
        userRepository.save(currentUser);
        return UserInfoResponse
                .builder()
                .fullName(userInfo.getFullName())
                .biography(userInfo.getBiography())
                .gender(userInfo.getGender())
                .image(userInfo.getImage())
                .build();
    }

    @Override
    public UserInfoResponse findUserInfoByUserId(Long userId) {
        User currentUser = jwtService.checkAuthentication();
        if (!currentUser.getId().equals(userId)) {
            throw new RuntimeException("This user is not the current user");
        }
        UserInfo userInfo = currentUser.getUserInfo();
        if (userInfo == null) {
            return UserInfoResponse.builder().fullName("").biography("").gender(null).image(null).build();
        }
        return UserInfoResponse
                .builder()
                .fullName(userInfo.getFullName())
                .biography(userInfo.getBiography())
                .gender(userInfo.getGender())
                .image(userInfo.getImage())
                .build();
    }

    @Override
    public SimpleResponse updateUserInfo(Long userId, UserInfoUpdate infoUpdateRequest) {
        User currentUser = jwtService.checkAuthentication();

        if (!currentUser.getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't update another user-info");
        }

        UserInfo userInfo = currentUser.getUserInfo();
        if (userInfo == null) {
            userInfo = new UserInfo();
            userInfo.setUser(currentUser);
            currentUser.setUserInfo(userInfo);
        }

        userInfo.setFullName(infoUpdateRequest.fullname());
        userInfo.setBiography(infoUpdateRequest.biography());
        userInfo.setGender(infoUpdateRequest.gender());

        userRepository.save(currentUser);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("User's info is updated")
                .build();
    }


    @Override
    @Transactional
    public SimpleResponse changeImage(Long userId, AvatarRequest avatar) {
        User currentUser = jwtService.checkAuthentication();

        if (!currentUser.getId().equals(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You can't update another user-info"
            );
        }

        UserInfo userInfo = currentUser.getUserInfo();

        // если UserInfo ещё нет- создаём
        if (userInfo == null) {
            userInfo = new UserInfo();
            userInfo.setUser(currentUser);
        }

        // обновляем image
        userInfo.setImage(avatar.image());
        userInfoRepository.save(userInfo);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(userInfo.getId() == null ? "User image added" : "User image changed")
                .build();
    }

    @Override
    public SimpleResponse deleteImage(Long userId) {
        User currentUser = jwtService.checkAuthentication();

        if (!currentUser.getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't delete another user-info");
        }

        UserInfo userInfo = currentUser.getUserInfo();

        if (userInfo == null || userInfo.getImage() == null || userInfo.getImage().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User's image is empty");
        }

        userInfo.setImage(null);
        userInfoRepository.save(userInfo);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("User's avatar is deleted")
                .build();
    }
}
