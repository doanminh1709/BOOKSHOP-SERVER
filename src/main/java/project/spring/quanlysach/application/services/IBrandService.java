package project.spring.quanlysach.application.services;

import project.spring.quanlysach.domain.dto.BrandDTO;
import project.spring.quanlysach.domain.entity.Brand;

import java.util.List;

public interface IBrandService {
    List<Brand> listBrand(Integer page, Integer size);

    Brand createNewBrand(BrandDTO brandDTO);

    String updateBrandById(BrandDTO brandDTO, Integer id);

    Brand getBrandById(Integer id);

    String deleteBrandById(Integer id);
}
