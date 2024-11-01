package Project.example.Project_1.enity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

@Entity
@Setter
@Getter
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String productName;

    @Column
    private Float price;

    @Column
    private int quantity;

    @Column
    private String note;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    Product product;


}
