package peaksoft.instaproject.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import peaksoft.instaproject.enums.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "users")

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder

public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    @SequenceGenerator(name = "user_gen", sequenceName = "user_seq", allocationSize = 1)
    Long id;

    @Column(unique = true, nullable = false)
    String userName;

    String password;

    @Column(unique = true, nullable = false)
    @Email(message = "Email должен сожержать символ '@' и иметь корректный формат")
    String email;

    String phoneNumber;

    @Enumerated(EnumType.STRING)
    Role role; //Admin or User

    int followersCount;  //мои подписЧИКИ
    int followingCount;  //мои подписКИ


    //todo ●●●●●●●●●● POSTS ●●●●●●●●●●
    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE})
    @JsonManagedReference //сериализуется только родительская сторона (часто исп-ся @OneToOne/@OneToMany)
            List<Post> posts = new ArrayList<>();

    //todo ●●●●●●●●●● COMMENT ●●●●●●●●●●
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    List<Comment> comments = new ArrayList<>();

    //todo ●●●●●●●●●● LIKE ●●●●●●●●●●
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE},
            orphanRemoval = true)
    @JsonManagedReference
    List<Like> likes = new ArrayList<>();

    //todo ●●●●●●●●●● USER_INFO ●●●●●●●●●●
    @OneToOne(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE})
    @JsonManagedReference
    UserInfo userInfo;

    @OneToOne(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE},
            orphanRemoval = true)
    @JsonManagedReference
    Follower follower;


    //todo ❁❁❁❁❁❁❁❁❁❁ SECURITY ❁❁❁❁❁❁❁❁❁❁
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public String getPassword() {
        return password;
    }
}


