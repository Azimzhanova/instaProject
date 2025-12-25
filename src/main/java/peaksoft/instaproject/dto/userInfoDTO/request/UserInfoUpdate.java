package peaksoft.instaproject.dto.userInfoDTO.request;

import peaksoft.instaproject.enums.Gender;

public record UserInfoUpdate (
    String fullname,
    String biography,
    Gender gender){
}
