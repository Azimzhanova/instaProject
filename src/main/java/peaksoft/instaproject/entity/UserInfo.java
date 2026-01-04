package peaksoft.instaproject.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import peaksoft.instaproject.enums.Gender;

@Entity
@Table(name = "userInfos")

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userInfo_gen")
    @SequenceGenerator(name = "userInfo_gen", sequenceName = "userInfo_seq", allocationSize = 1)
    Long id;

    String fullName;
    String biography;

    @Enumerated(EnumType.STRING)
    Gender gender;

    String image;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId //для того, чтобы  user_id = userInfo_id
    @JsonBackReference
    User user;
}
