package Project.example.Project_1.request;

import Project.example.Project_1.enums.EnumStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlogCreateRequest {

    @NotNull(message = "Content cannot be null")
    String content;
    @NotNull(message = "BlogName cannot be null")
    @Size(min = 5)
    String blogName;
    @Enumerated(EnumType.STRING)
    EnumStatus status;
    LocalDate date;
    @NotNull(message = "Image cannot be null")
    String image;
    @NotNull(message = "Description cannot be null")
    @Size(min = 5)
    String description;
    String createdBy;
}

