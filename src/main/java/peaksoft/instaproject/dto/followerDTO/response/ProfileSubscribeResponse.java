package peaksoft.instaproject.dto.followerDTO.response;

import lombok.Builder;

@Builder
public record ProfileSubscribeResponse (
        Long targetUserId,
        boolean subscribed,
        String button){}

