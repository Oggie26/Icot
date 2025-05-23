package Project.example.Project_1.enity;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bookOrder_id")
    @JsonIgnore
    BookOrder bookOrder;

    @OneToMany(mappedBy = "typePrint", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> products;
}
