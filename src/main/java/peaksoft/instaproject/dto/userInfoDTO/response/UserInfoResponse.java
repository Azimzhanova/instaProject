package peaksoft.instaproject.dto.userInfoDTO.response;

import lombok.Builder;
import peaksoft.instaproject.enums.Gender;

@Builder
public record UserInfoResponse(

        String fullName,
        String biography,
        Gender gender,
        String image
) { }
