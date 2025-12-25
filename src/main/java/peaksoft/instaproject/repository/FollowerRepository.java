package peaksoft.instaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.instaproject.dto.followerDTO.response.FollowerUserResponse;
import peaksoft.instaproject.entity.Follower;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowerRepository extends JpaRepository<Follower,Long> {

    Optional<Follower> findByUser_Id(Long userID);

    @Query("select new peaksoft.instaproject.dto.followerDTO.response.FollowerUserResponse (u.userName, ui.image) from Follower f join f.subscribers u join u.userInfo ui where f.user.id = :userId ")
    List<FollowerUserResponse> findSubscribers(Long userId);

    @Query("select new peaksoft.instaproject.dto.followerDTO.response.FollowerUserResponse (u.userName, ui.image) from Follower f join f.subscriptions u join u.userInfo ui where f.user.id = :userId")
    List<FollowerUserResponse> findSubscriptions(Long userId);
}
