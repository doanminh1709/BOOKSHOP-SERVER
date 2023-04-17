package project.spring.quanlysach.application.services;

import project.spring.quanlysach.domain.dto.CategoryDTO;
import project.spring.quanlysach.domain.entity.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> findAllListCategory(Integer page, Integer size);

    Category createNewCategory(CategoryDTO categoryDTO);

    String updateCategoryById(CategoryDTO categoryDTO, Integer id);

    Category getCategoryById(Integer id);

    String deleteCategoryById(Integer id);
}
