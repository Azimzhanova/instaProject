package peaksoft.instaproject.dto.userInfoDTO.request;


import peaksoft.instaproject.enums.Gender;

public record UserInfoRequest(
        String fullName,
        String biography,
        Gender gender,
        String image
) { }
