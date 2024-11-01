package Project.example.Project_1.enity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private LocalDate orderDate;

    @Column
    private Float total;

    @Column
    private String address;

    @Column
    private String customerName;

}
