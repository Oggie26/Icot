package Project.example.Project_1.enity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import java.util.List;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String productName;

    @Column
    private String image;

    @Column
    private Boolean status;

    @Column
    private Float price;

    @Column
    private String description;

    @Column
    private int purchases;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<OrderDetail> orderDetail;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", unique = true)
    @JsonIgnore
    Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<Topping> topping;

}
