package Project.example.Project_1.enity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
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


}
