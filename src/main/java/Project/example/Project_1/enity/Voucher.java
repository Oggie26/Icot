package Project.example.Project_1.enity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String code;

    @Column
    private String voucherName;

    @Column
    private Float discountAmount;

    @Column
    private int point;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column
    private int MaxUsers;

    @Column
    private Boolean status;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", unique = true)
    @JsonIgnore
    Order order;
}
