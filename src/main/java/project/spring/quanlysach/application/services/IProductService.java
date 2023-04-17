package project.spring.quanlysach.application.services;

import project.spring.quanlysach.domain.dto.CategoryDTO;
import project.spring.quanlysach.domain.dto.ProductDTO;
import project.spring.quanlysach.domain.entity.Category;
import project.spring.quanlysach.domain.entity.Product;

import java.util.List;

public interface IProductService {
    List<Product> findAllListProduct(Integer page, Integer size);

    Product createNewProduct(ProductDTO productDTO);

    String updateProductById(ProductDTO productDTO, Integer id);

    Product getProductById(Integer id);

    String deleteProductById(Integer id);

    List<Product> searchByName(String name);

    List<Product> showListProductByStatus(String status);

    List<Product> showListProductByCategory(int categoryId);

    List<Product> showListProductByBrand(int brandId);

    List<Product> softProductASCByPrice(Integer page, Integer size);

    List<Product> softProductDESCByPrice(Integer page, Integer size);

    List<Product> searchProductBetweenMoney(Float start, Float end);

}
