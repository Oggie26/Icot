package Project.example.Project_1.response;

import Project.example.Project_1.enums.EnumStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogResponse {
    Long id;
    String blogName;
    String image;
    String description;
    @Enumerated(EnumType.STRING)
    EnumStatus status;
    LocalDate date;
    String content;
    String createdBy;
}

