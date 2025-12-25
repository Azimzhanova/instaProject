package peaksoft.instaproject.service;

import peaksoft.instaproject.dto.followerDTO.response.FollowerUserResponse;
import peaksoft.instaproject.dto.followerDTO.response.ProfileSubscribeResponse;

import java.util.List;

public interface FollowerService {
    List<FollowerUserResponse> search(String name);
    ProfileSubscribeResponse subscribe(Long targetId);
    List<FollowerUserResponse> getAllSubscribedByUserId(Long userId);
    List<FollowerUserResponse> getAllSubscriptionsByUserId(Long userId);
}
