package Project.example.Project_1.enity;


import Project.example.Project_1.enums.EnumStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tags")
public class Tag extends AbstractEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    String name;

    @Enumerated(EnumType.STRING)
    EnumStatus status;

    @ManyToMany(mappedBy = "tags")
    private List<DesignTemplate> designTemplates = new ArrayList<>();
}
