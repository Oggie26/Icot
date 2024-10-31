package Project.example.Project_1.request;

import Project.example.Project_1.enums.EnumSize;
import lombok.Data;

@Data
public class CategoryRequest {
    private String name;
    private Float price;
    private EnumSize size;
    private Boolean status;
}
