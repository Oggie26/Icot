package Project.example.Project_1.enity;

import Project.example.Project_1.enums.EnumMaterial;
import Project.example.Project_1.enums.EnumStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class Fabric extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    String fabricName;

    @Column
    Double price;

    @Enumerated(EnumType.STRING)
    EnumStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "product_id")
    Product product;

}
