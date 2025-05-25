package Project.example.Project_1.enity;

import Project.example.Project_1.enums.EnumSize;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookOrder extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    EnumSize size;

    @Column
    Integer quantity;

    @Column
    String description;

    @Column
    String color;

    @Column
    Double totalPrice;

    //Category
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "category_id")
    Category category;

    //Fabric
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "fabric_id")
    Fabric fabric;

    //Kieu In muc
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "typePrint_id")
    TypePrint typePrint;

    //ImageCus
    @OneToMany(mappedBy = "bookOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    List<ImageCus> imageCus;

    //User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    User user;

    @OneToMany(mappedBy = "bookOrder")
    @JsonIgnore
    List<ProcessOrder> processOrders;
}
