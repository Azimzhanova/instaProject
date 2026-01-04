package peaksoft.instaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.instaproject.dto.userDTO.response.UserResponse;
import peaksoft.instaproject.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
    boolean existsUserByEmail(String email);

    @Query("select new peaksoft.instaproject.dto.userDTO.response.UserResponse(u.userName,u.email, u.phoneNumber) from User u")
    List<UserResponse> getAllUsers();

    @Query("select u from User u left join u.userInfo ui where lower(u.userName)" +
            "like lower(concat('%', :someName, '%')) or lower(ui.fullName)" +
            "like lower(concat('%', :someName, '%'))")
    List<User> searchUserByUsernameOrByFullName(String someName);

}
