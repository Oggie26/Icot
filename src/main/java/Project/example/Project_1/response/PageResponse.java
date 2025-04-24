package Project.example.Project_1.response;

import Project.example.Project_1.enums.EnumRole;
import Project.example.Project_1.enums.EnumStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {
    private List<T> content;
    private int pageNumber;
    private int totalPages;
    private long totalElements;
}
