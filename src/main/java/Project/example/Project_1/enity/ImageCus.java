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
public class ImageCus {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column
    String url;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bookOrder_id")
    @JsonIgnore
    BookOrder bookOrder;
}
