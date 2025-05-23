package Project.example.Project_1.enity;

import Project.example.Project_1.enums.EnumStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "design_templates")
public class DesignTemplate extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    String description;

    @Column
    String name;

    @Column
    String createdByDesigner;

    @Column
    String image;

    @Column
    Float width;

    @Column
    Float height;

    @Enumerated(EnumType.STRING)
    EnumStatus status;

    @ManyToMany
    @JoinTable(
            name = "design_template_tags",
            joinColumns = @JoinColumn(name = "template_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();
}
