package Project.example.Project_1.enity;

import Project.example.Project_1.enums.EnumSize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private EnumSize size;

    @Column
    private float price;

    @Column
    private Boolean status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", unique = true)
    @JsonIgnore
    Product product;
}
