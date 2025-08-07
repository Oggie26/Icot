package Project.example.Project_1.request;

import lombok.Data;

import java.util.List;

@Data
public class DesignerUploadRequest {
    private List<ImageRequest> imageDesign;

}
