package project.spring.quanlysach.application.services.imp;

import com.github.slugify.Slugify;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import project.spring.quanlysach.application.constants.DevMessageConstant;
import project.spring.quanlysach.application.constants.UserMessConstant;
import project.spring.quanlysach.application.mapper.ProductMapper;
import project.spring.quanlysach.application.repo.BrandRepository;
import project.spring.quanlysach.application.repo.CategoryRepository;
import project.spring.quanlysach.application.repo.ProductRepository;
import project.spring.quanlysach.application.services.IProductService;
import project.spring.quanlysach.config.exception.VsException;
import project.spring.quanlysach.domain.dto.ProductDTO;
import project.spring.quanlysach.domain.entity.Brand;
import project.spring.quanlysach.domain.entity.Category;
import project.spring.quanlysach.domain.entity.Product;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImpProductService implements IProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final Slugify slugify;

    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    public ImpProductService(ProductRepository productRepository, BrandRepository brandRepository,
                             CategoryRepository categoryRepository, Slugify slugify) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.slugify = slugify;
    }

    @Override
    public List<Product> findAllListProduct(Integer page, Integer size) {
        List<Product> listProduct;
        if (page != null) {
            listProduct = productRepository.findAll(PageRequest.of(page.intValue(), size)).getContent();
        } else {
            listProduct = productRepository.findAll();
        }
        return listProduct;
    }

    @Override
    public Product createNewProduct(ProductDTO productDTO) {
        Product foundProduct = productRepository.findProductByName(productDTO.getName());
        if (foundProduct != null) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.DUPLICATE_NAME, productDTO.getName()));
        }
        Optional<Brand> foundBrand = brandRepository.findById(productDTO.getBrandId());
        Optional<Category> foundCategory = categoryRepository.findById(productDTO.getCategoryId());

        if (foundBrand.isEmpty() && foundCategory.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.OBJECT_NOT_FOUND));
        } else {
            if (foundBrand.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "brand", productDTO.getBrandId()));
            }
            if (foundCategory.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "category", productDTO.getCategoryId()));
            }
            Product newProduct = productMapper.toProduct(productDTO,
                    productDTO.getCategoryId(),
                    productDTO.getBrandId());
            newProduct.setSeoTitle(slugify.slugify(productDTO.getSeoTitle()));
            return productRepository.save(newProduct);
        }
    }

    @Override
    public String updateProductById(ProductDTO productDTO, Integer id) {
        Optional<Product> foundProduct = productRepository.findById(id);

        if (foundProduct.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "Product", id));
        }
        Optional<Brand> foundBrand = brandRepository.findById(productDTO.getBrandId());
        Optional<Category> foundCategory = categoryRepository.findById(productDTO.getCategoryId());

        if (foundBrand.isEmpty() && foundCategory.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.OBJECT_NOT_FOUND));
        } else {
            if (foundBrand.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "brand", productDTO.getBrandId()));
            }
            if (foundCategory.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "category", productDTO.getCategoryId()));
            }
            foundProduct = Optional.ofNullable(productMapper.toProduct(productDTO,
                    productDTO.getCategoryId(),
                    productDTO.getBrandId()));
            foundProduct.get().setId(id);
            foundProduct.get().setSeoTitle(slugify.slugify(productDTO.getSeoTitle()));
            foundProduct.get().setCreateDate(foundProduct.get().getCreateDate());
            foundProduct.get().setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
            productRepository.save(foundProduct.get());
            return DevMessageConstant.Common.NOTIFICATION_UPDATE_SUCCESS;
        }
    }

    @Override
    public Product getProductById(Integer id) {
        Optional<Product> foundProduct = productRepository.findById(id);

        if (foundProduct.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "Product", id));
        }
        return foundProduct.get();
    }

    @Override
    public String deleteProductById(Integer id) {
        Optional<Product> foundProduct = productRepository.findById(id);

        if (foundProduct.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "Product", id));
        }
        foundProduct.get().setDeleteFlag(Boolean.TRUE);
        foundProduct.get().setActiveFlag(Boolean.FALSE);
        productRepository.save(foundProduct.get());
        return DevMessageConstant.Common.NOTIFICATION_UPDATE_SUCCESS;
    }

    @Override
    public List<Product> searchByName(String name) {
        List<Product> foundProduct = productRepository.searchProductByName("%" + name + "%");
        if (foundProduct == null) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NO_DATA_SELECTED));
        }
        return foundProduct;
    }

    @Override
    public List<Product> showListProductByStatus(String status) {

        String inputFormat = status.trim().toLowerCase();
        List<Product> foundProduct = productRepository.findProductByStatus(inputFormat);
        if (foundProduct == null) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NO_DATA_SELECTED));
        }
        return foundProduct;
    }

    @Override
    public List<Product> showListProductByCategory(int categoryId) {
        Optional<Category> foundCategory = categoryRepository.findById(categoryId);
        if (foundCategory.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "category", categoryId));
        }
        return productRepository.findProductByCategoryId(categoryId);
    }

    @Override
    public List<Product> showListProductByBrand(int brandId) {
        Optional<Brand> foundBrand = brandRepository.findById(brandId);

        if (foundBrand.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "brand", brandId));
        }
        return productRepository.findProductByBrandId(brandId);
    }


    @Override
    public List<Product> softProductASCByPrice(Integer page, Integer size) {
        List<Product> listProduct;
        if (page != null) {
            listProduct = productRepository.findAll(PageRequest.of(page.intValue(), size)).getContent()
                    .stream().sorted(Comparator.comparing(Product::getPrice)).collect(Collectors.toList());
        } else {
            listProduct = productRepository.findAll(Sort.by(Sort.Direction.ASC, "price"));
        }
        return listProduct;
    }

    @Override
    public List<Product> softProductDESCByPrice(Integer page, Integer size) {
        List<Product> listProduct;
        if (page != null) {
            listProduct = productRepository.findAll(PageRequest.of(page.intValue(), size)).getContent()
                    .stream().sorted(Comparator.comparing(Product::getPrice).reversed()).collect(Collectors.toList());
        } else {
            listProduct = productRepository.findAll(Sort.by(Sort.Direction.DESC, "price"));
        }
        return listProduct;
    }

    @Override
    public List<Product> searchProductBetweenMoney(Float start, Float end) {
        //Check case 1 in 2 parameter null
        if (start == null && end != null) {
            return productRepository.selectProductSmallerPrice(end);
        }
        if (start != null && end == null) {
            return productRepository.selectProductHigherPrice(start);
        }
        Optional<List<Product>> listProduct = Optional.ofNullable(productRepository.selectProductBetweenPrice(start, end));
        if (listProduct.isPresent()) {
            return listProduct.get();
        } else {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT, DevMessageConstant.Common.NO_DATA_SELECTED);
        }
    }
}
