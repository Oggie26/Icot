package Project.example.Project_1.enity;

import Project.example.Project_1.enums.EnumPaymentMethod;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private EnumPaymentMethod method;

    @Column
    private LocalDate date;

    @Column
    private Float price;

    @OneToMany(mappedBy = "payment")
    @JsonIgnore
    List<Order> order;
}
