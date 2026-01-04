package peaksoft.instaproject.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "images")

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_gen")
    @SequenceGenerator(name = "image_gen", sequenceName = "image_seq", allocationSize = 1)
    Long id;

    String imageUrl;

    @ManyToOne
    Post post;
}
