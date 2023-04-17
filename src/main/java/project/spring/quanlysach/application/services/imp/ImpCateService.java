package project.spring.quanlysach.application.services.imp;

import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.spring.quanlysach.application.constants.DevMessageConstant;
import project.spring.quanlysach.application.constants.UserMessConstant;
import project.spring.quanlysach.application.mapper.CateMapper;
import project.spring.quanlysach.application.repo.CateRepository;
import project.spring.quanlysach.application.services.ICateService;
import project.spring.quanlysach.config.exception.VsException;
import project.spring.quanlysach.domain.dto.CateDTO;
import project.spring.quanlysach.domain.entity.Cate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ImpCateService implements ICateService {

    private final CateRepository cateRepository;
    private final CateMapper cateMapper = Mappers.getMapper(CateMapper.class);

    public ImpCateService(CateRepository cateRepository) {
        this.cateRepository = cateRepository;
    }

    @Override
    public List<Cate> listCate(Integer page, Integer size) {
        List<Cate> listCate;
        if (page != null) {
            listCate = cateRepository.findAll(PageRequest.of(page.intValue(), size)).getContent();
        } else {
            listCate = cateRepository.findAll();
        }
        return listCate;
    }
    @Override
    public Cate createNewCate(CateDTO cateDTO) {
        Cate cateName = cateRepository.findByName(cateDTO.getName());
        if (cateName != null) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.DUPLICATE_NAME, cateDTO.getName()));
        } else {
            Cate newCate = cateMapper.toCate(cateDTO);
            return cateRepository.save(newCate);
        }
    }

    @Override
    public String updateCateById(CateDTO cateDTO, Integer id) {
        Optional<Cate> foundCate = cateRepository.findById(id);
        if (foundCate.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "cate", id));
        } else {
            Cate cate = cateRepository.findByName(cateDTO.getName());
            if (cate != null && !Objects.equals(cate.getName(), foundCate.get().getName())) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.DUPLICATE_NAME, cateDTO.getName()));
            } else {
                foundCate = Optional.ofNullable(cateMapper.toCate(cateDTO));
                foundCate.get().setId(id);
                cateRepository.save(foundCate.get());
                return "Update successfully";
            }
        }
    }

    @Override
    public Cate getCateById(Integer id) {
        Optional<Cate> foundCate = cateRepository.findById(id);
        if (foundCate.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "cate", id));
        }
        return foundCate.get();
    }

    @Override
    public String deleteCateById(Integer id) {
        Optional<Cate> foundById = cateRepository.findById(id);
        if (foundById.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "cate", id));
        } else {
            foundById.get().setActiveFlag(Boolean.FALSE);
            foundById.get().setDeleteFlag(Boolean.TRUE);
            cateRepository.save(foundById.get());
        }
        return "Upset active of cate successfully!";
    }
}
