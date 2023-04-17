package project.spring.quanlysach.application.services.imp;

import com.github.slugify.Slugify;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.spring.quanlysach.application.constants.DevMessageConstant;
import project.spring.quanlysach.application.constants.UserMessConstant;
import project.spring.quanlysach.application.mapper.CategoryMapper;
import project.spring.quanlysach.application.repo.CategoryRepository;
import project.spring.quanlysach.application.services.ICategoryService;
import project.spring.quanlysach.config.exception.InvalidException;
import project.spring.quanlysach.config.exception.VsException;
import project.spring.quanlysach.domain.dto.CategoryDTO;
import project.spring.quanlysach.domain.entity.Category;

import java.util.List;
import java.util.Optional;

@Service
public class ImpCategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    private final Slugify slugify;//format to content suit with seo

    private final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);

    public ImpCategoryService(CategoryRepository categoryRepository, Slugify slugify) {
        this.categoryRepository = categoryRepository;
        this.slugify = slugify;
    }

    @Override
    public List<Category> findAllListCategory(Integer page, Integer size) {
        List<Category> listAll;
        if (page != null) {
            listAll = categoryRepository.findAll(PageRequest.of(page, size)).getContent();
        } else {
            listAll = categoryRepository.findAll();
        }
        return listAll;
    }
    @Override
    public Category createNewCategory(CategoryDTO categoryDTO) {
        Category foundCategory = categoryRepository.findCategoriesByName(categoryDTO.getName());
        if (foundCategory != null) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.DUPLICATE_NAME, categoryDTO.getName()));
        }
        Category newCategory = categoryMapper.toCategory(categoryDTO);
        newCategory.setSeoTitle(slugify.slugify(categoryDTO.getSeoTitle()));
        return categoryRepository.save(newCategory);
    }

    @Override
    public String updateCategoryById(CategoryDTO categoryDTO, Integer id) {
        Optional<Category> categoryById = categoryRepository.findById(id);
        if (categoryById.isPresent()) {
            categoryById = Optional.ofNullable((categoryMapper.toCategory(categoryDTO)));
            categoryById.get().setId(id);
            categoryRepository.save(categoryById.get());
            return "Update successfully";
        } else {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "category", id));
        }
    }
    @Override
    public Category getCategoryById(Integer id) {
        Optional<Category> foundById = categoryRepository.findById(id);
        if (foundById.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "category", id));
        } else {
            return foundById.get();
        }
    }

    @Override
    public String deleteCategoryById(Integer id) {
        Optional<Category> foundById = categoryRepository.findById(id);
        if (foundById.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "category", id));
        } else {
            foundById.get().setActiveFlag(Boolean.FALSE);
            foundById.get().setDeleteFlag(Boolean.TRUE);
            categoryRepository.save(foundById.get());
        }
        return "Upset active of category successfully!";
    }
}
