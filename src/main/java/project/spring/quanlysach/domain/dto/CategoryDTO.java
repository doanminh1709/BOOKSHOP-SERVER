package project.spring.quanlysach.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private String name;

    private String seoTitle;

    private int patentId;

    private String metaKeyWord;

    private int quantity;

    private boolean hot;
    //@Nationalized
    private String description;

    private String detail;
}
