package Project.example.Project_1.enity;

import Project.example.Project_1.enums.EnumStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class ProcessOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private EnumStatus status;


}
