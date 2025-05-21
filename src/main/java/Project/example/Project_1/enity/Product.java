package Project.example.Project_1.enity;

import Project.example.Project_1.enums.EnumSize;
import Project.example.Project_1.enums.EnumStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    String imageThumbnail;

    @Enumerated(EnumType.STRING)
    EnumSize size;

    @Column
    String color;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    EnumStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "category_id")
    Category category;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    @JsonIgnore
    List<Feedback> feedbacks;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    @JsonIgnore
    List<Image> images;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "fabric_id")
    Fabric fabric;

}
