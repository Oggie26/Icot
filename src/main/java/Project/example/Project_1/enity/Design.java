package Project.example.Project_1.enity;

import Project.example.Project_1.enums.EnumStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Auditable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Design extends AbstractEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    EnumStatus status;

    @Column
    String description;

    @Column
    String fileUrl;

    @Column
    String designName;

    @Column
    String fileName;

    @ManyToMany
    @JoinTable(
            name = "design_tags",
            joinColumns = @JoinColumn(name = "design_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();

    @OneToOne(mappedBy = "design")
    @JsonIgnore
    BookOrder bookOrder;

    @ManyToOne
    User user;



}
