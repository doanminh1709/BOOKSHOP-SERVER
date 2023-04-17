package project.spring.quanlysach.domain.dto;

import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostCommentDTO {
    private int customerId;
    private String detail;
    private String feeling;
    private String linkUrl;
    private int postId;

}
