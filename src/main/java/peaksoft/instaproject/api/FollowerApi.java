package peaksoft.instaproject.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.instaproject.dto.followerDTO.response.FollowerUserResponse;
import peaksoft.instaproject.dto.followerDTO.response.ProfileSubscribeResponse;
import peaksoft.instaproject.service.FollowerService;

import java.util.List;
@RestController
@RequestMapping("/api/followers")
@RequiredArgsConstructor
public class FollowerApi {
    private final FollowerService followerService;

    //todo search by username or full name
    @GetMapping("/search-user")
    public List<FollowerUserResponse> searchByUsernameOrFullName(@RequestParam String someName) {
        return followerService.search(someName);
    }

    //todo 'Подписаться' 'Отменить подписку
    @PostMapping("/subscribe")
    public ProfileSubscribeResponse subscribe(@RequestParam Long targetId) {
        return followerService.subscribe(targetId);
    }

    //todo get all Subscribers by user-id
    @GetMapping("/{userId}/find-subscribers")
    public List<FollowerUserResponse> getSubscribers(@PathVariable Long userId) {
        return followerService.getAllSubscribedByUserId(userId);
    }

    //todo get all Subscriptions by user-id
    @GetMapping("/{userId}/get-subscriptions")
    public List<FollowerUserResponse> getSubscriptions(@PathVariable Long userId) {
        return followerService.getAllSubscriptionsByUserId(userId);
    }
}
