package Project.example.Project_1.enity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "payment_id")
    Payment payment;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderDetail> orderDetail = new ArrayList<>();

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<ProcessOrder> processOrders = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "voucher_id", unique = true)
    @JsonIgnore
    Voucher voucher;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<Store> store = new ArrayList<>();


}
