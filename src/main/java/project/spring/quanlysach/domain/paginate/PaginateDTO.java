package project.spring.quanlysach.domain.paginate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaginateDTO<T> {

        public Page<T> pageData;

        private Pagination pagination;
}
