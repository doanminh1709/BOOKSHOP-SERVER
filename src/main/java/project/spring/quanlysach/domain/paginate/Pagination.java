package project.spring.quanlysach.domain.paginate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Pagination {
    private Integer page;

    private Integer perPage;

    private Integer lastPage;

    private Long total;
}
