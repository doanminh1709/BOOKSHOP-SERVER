package project.spring.quanlysach.domain.paginate;

public class PaginationResponseDTO<T> {
    private Integer status;
    private String message;

    private PaginationDTO<T> result;
}
