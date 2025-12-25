package peaksoft.instaproject.dto.followerDTO.response;

import lombok.Builder;

@Builder
public record FollowerUserResponse (
        String userName,
        String image){}

