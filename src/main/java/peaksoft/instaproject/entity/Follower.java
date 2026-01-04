package peaksoft.instaproject.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "followers")

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "follower_gen")
    @SequenceGenerator(name = "follower_gen", sequenceName = "follower_seq", allocationSize = 1)
    Long id;

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JsonBackReference //не сериализуется дочерняя сторона(не будет рекурсий)
    User user;

    @ManyToMany
    List<User> subscribers = new ArrayList<>(); //подписчик

    @ManyToMany
    List<User> subscriptions = new ArrayList<>(); //подписка
}

