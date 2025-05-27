package Project.example.Project_1.enity;

import Project.example.Project_1.enums.EnumStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TypePrint extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String printName;

    @Column
    Double price;

    @Enumerated(EnumType.STRING)
    EnumStatus status;

    @OneToMany(mappedBy = "typePrint", fetch = FetchType.EAGER)
    @JsonIgnore
    List<BookOrder> bookOrders;

    @OneToMany(mappedBy = "typePrint", fetch = FetchType.EAGER)
    @JsonIgnore
    List<Product> products;


}
