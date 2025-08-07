package Project.example.Project_1.enity;

import Project.example.Project_1.enums.EnumStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Category extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column
    String categoryName;

    @Column
    String description;

    @Enumerated(EnumType.STRING)
    EnumStatus status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category", fetch = FetchType.EAGER)
    @JsonIgnore
    List<Product> products;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category", fetch = FetchType.EAGER)
    @JsonIgnore
    List<BookOrder> bookOrders;

}
