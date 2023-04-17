package project.spring.quanlysach.application.services.imp;

import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.spring.quanlysach.application.constants.DevMessageConstant;
import project.spring.quanlysach.application.constants.UserMessConstant;
import project.spring.quanlysach.application.mapper.BrandMapper;
import project.spring.quanlysach.application.repo.BrandRepository;
import project.spring.quanlysach.application.services.IBrandService;
import project.spring.quanlysach.config.exception.VsException;
import project.spring.quanlysach.domain.dto.BrandDTO;
import project.spring.quanlysach.domain.entity.Brand;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ImpBrandService implements IBrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper = Mappers.getMapper(BrandMapper.class);

    public ImpBrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public List<Brand> listBrand(Integer page, Integer size) {
        List<Brand> listBrands;
        if (page != null) {
            listBrands = brandRepository.findAll(PageRequest.of(page.intValue(), size)).getContent();
        } else {
            listBrands = brandRepository.findAll();
        }
        return listBrands;
    }

    @Override
    public Brand createNewBrand(BrandDTO brandDTO) {
        Brand brandName = brandRepository.findByName(brandDTO.getName());
        if (brandName != null) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.DUPLICATE_NAME, brandName.getName()));
        } else {
            Brand newBrand = brandMapper.toBrand(brandDTO);
            return brandRepository.save(newBrand);
        }
    }

    @Override
    public String updateBrandById(BrandDTO brandDTO, Integer id) {
        Optional<Brand> foundBrand = brandRepository.findById(id);
        if (foundBrand.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "brand", id));
        } else {
            Brand brand = brandRepository.findByName(brandDTO.getName());
            if (brand != null && !Objects.equals(brand.getName(), foundBrand.get().getName())) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.EXITS_NAME, brandDTO.getName()));
            } else {
                foundBrand = Optional.ofNullable(brandMapper.toBrand(brandDTO));
                foundBrand.get().setId(id);
                brandRepository.save(foundBrand.get());
                return "Update successfully";
            }
        }
    }

    @Override
    public Brand getBrandById(Integer id) {
        Optional<Brand> foundBrand = brandRepository.findById(id);
        if (foundBrand.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "brand", id));
        }
        return foundBrand.get();
    }

    @Override
    public String deleteBrandById(Integer id) {
        Optional<Brand> foundById = brandRepository.findById(id);
        if (foundById.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "brand", id));
        } else {
            foundById.get().setActiveFlag(Boolean.FALSE);
            foundById.get().setDeleteFlag(Boolean.TRUE);
            brandRepository.save(foundById.get());
        }
        return "Upset active of brand successfully!";
    }
}
