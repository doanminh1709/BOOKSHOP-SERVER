package project.spring.quanlysach.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostDTO {
    private String name;

    private String seoTitle;

    private boolean status;

    private String description;

    private String detail;

    private int brandId;

    private int cateId;
    private int tagId;

}
