package Project.example.Project_1.config;
import Project.example.Project_1.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PageMapper {
    public <E, D> PageResponse<D> convertToPageResponse(Page<E> entityPage, Function<E, D> mapper) {
        List<D> dtoList = entityPage.getContent().stream()
                .map(mapper)
                .collect(Collectors.toList());

        return new PageResponse<>(
                dtoList,
                entityPage.getNumber(),
                entityPage.getTotalPages(),
                entityPage.getTotalElements()
        );
    }
}

