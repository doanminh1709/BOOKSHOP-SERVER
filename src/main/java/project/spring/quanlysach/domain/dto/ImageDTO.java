package project.spring.quanlysach.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImageDTO {
    private String title;
    private String path;
    private Integer postId;
    private Integer productId;
}
