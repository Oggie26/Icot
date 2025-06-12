package Project.example.Project_1.enity;
import Project.example.Project_1.enums.DiscountType;
import Project.example.Project_1.enums.EnumStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "tbl_voucher")
public class Voucher extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "code")
    String code;

    @Enumerated(EnumType.STRING)
    DiscountType discountType;

    @Column(name = "discount")
    Integer discount;

    @Column(name = "min_order_value")
    Double minOrderValue;

    @Column(name = "description")
    String description;

    @Column(name = "point")
    Integer point;

    @Enumerated(EnumType.STRING)
    EnumStatus status;

    @ManyToMany(mappedBy = "vouchers", cascade = CascadeType.ALL)
    @JsonIgnore
    List<User> users;

    public void addUser(User obj){
        users.add(obj);
    }

}
