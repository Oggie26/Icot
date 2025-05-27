package Project.example.Project_1.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TypePrintRequest {
    Double price;
    String name;
}
