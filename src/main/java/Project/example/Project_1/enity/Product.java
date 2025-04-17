package Project.example.Project_1.enity;

import Project.example.Project_1.enums.EnumSize;
import Project.example.Project_1.enums.EnumStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column
    String productName;

    @Column(name = "price")
    Double price;

    @Column
    String description;

    @Column
    String image;

    @Enumerated(EnumType.STRING)
    EnumSize size;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    EnumStatus status;
}
