package Project.example.Project_1.enity;

import Project.example.Project_1.enums.EnumStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blog")
public class Blog extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "content", columnDefinition = "TEXT", length = 16000)
    @NotNull(message = "Content cannot be null")
    @Size(min = 20)
    String content;

    @Column
    @NotNull(message = "BlogName cannot be null")
    @Size(min = 5)
    String blogName;

    @Column(length = 16000)
    @NotNull(message = "Description cannot be null")
    @Size(min = 5)
    @Lob
    String description;

    @Column
    @NotNull(message = "Image cannot be null")
    String image;

    @Enumerated(EnumType.STRING)
    EnumStatus status;

    @Column
    LocalDateTime date;

    @Column
    String createdBy;

}

