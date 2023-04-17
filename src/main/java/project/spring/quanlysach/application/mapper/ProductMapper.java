package project.spring.quanlysach.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.boot.context.properties.bind.Name;
import project.spring.quanlysach.domain.dto.ProductDTO;
import project.spring.quanlysach.domain.entity.Brand;
import project.spring.quanlysach.domain.entity.Category;
import project.spring.quanlysach.domain.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mappings({
            @Mapping(target = "name", source = "productDTO.name"),
            @Mapping(target = "seoTitle", source = "productDTO.seoTitle"),
            @Mapping(target = "status", source = "productDTO.status"),
            @Mapping(target = "price", source = "productDTO.price"),
            @Mapping(target = "promotionPrice", source = "productDTO.promotionPrice"),
            @Mapping(target = "vat", source = "productDTO.vat"),
            @Mapping(target = "quantity", source = "productDTO.quantity"),
            @Mapping(target = "category", source = "categoryId", qualifiedByName = "productOfCategory"),
            @Mapping(target = "brand", source = "brandId", qualifiedByName = "productOfBrand"),

    })
    Product toProduct(ProductDTO productDTO,
                      @Name("productOfCategory") int categoryId,
                      @Name("productOfBrand") int brandId);

    @Named("productOfCategory")
    static Category toCategory(int categoryId) {
        Category category = new Category();
        category.setId(categoryId);
        return category;
    }

    @Named("productOfBrand")
    static Brand toBrand(int brandId) {
        Brand brand = new Brand();
        brand.setId(brandId);
        return brand;
    }

}
