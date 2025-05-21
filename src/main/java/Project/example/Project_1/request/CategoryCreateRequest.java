package Project.example.Project_1.request;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CategoryCreateRequest {
    String categoryName;
    String description;
}
