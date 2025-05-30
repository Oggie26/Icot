package Project.example.Project_1.enity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageCus extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column
    String image;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bookOrder_id")
    @JsonIgnore
    BookOrder bookOrder;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    User user;
}
