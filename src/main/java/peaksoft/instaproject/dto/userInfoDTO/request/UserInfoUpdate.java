package peaksoft.instaproject.dto.userInfoDTO.request;

import peaksoft.instaproject.enums.Gender;

public record UserInfoUpdate (
    String fullName,
    String biography,
    Gender gender){
}
