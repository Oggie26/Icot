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

public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "description")
    String description;

    @Column
    Integer rating;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "product_id")
    Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "user_id")
    User user;


}

