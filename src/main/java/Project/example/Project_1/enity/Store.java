package Project.example.Project_1.enity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

@Getter
@Setter
@Entity
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String storeName;

    @Column(unique = true)
    private String address;

    @Column
    private String image;

    @Column
    private Float revenue;

    @Column
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", unique = true)
    @JsonIgnore
    User user;

}
