package Project.example.Project_1.enity;


import Project.example.Project_1.enums.EnumProcess;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class ProcessOrder extends  AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    EnumProcess process;

    @Enumerated(EnumType.STRING)

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    Order order;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
