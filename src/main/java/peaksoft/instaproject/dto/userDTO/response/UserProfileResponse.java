package peaksoft.instaproject.dto.userDTO.response;

import lombok.Builder;
import peaksoft.instaproject.dto.postDTO.response.PostResponse;

import java.util.List;

@Builder
public record UserProfileResponse (
    String username,
    String fullname,
    String image,
    int subscribers,
    int subscriptions,
    List<PostResponse> posts ) {}