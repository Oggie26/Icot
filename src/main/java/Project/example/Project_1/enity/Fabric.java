package Project.example.Project_1.entity;

import Project.example.Project_1.enums.EnumMaterial;
import Project.example.Project_1.enums.EnumStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Fabric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    EnumMaterial material;

    @Column(name = "color")
    String color;

    @Column
    Double price;

    @Enumerated(EnumType.STRING)
    EnumStatus status;

}
