package project.spring.quanlysach.application.services.imp;

import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.spring.quanlysach.application.constants.DevMessageConstant;
import project.spring.quanlysach.application.constants.UserMessConstant;
import project.spring.quanlysach.application.mapper.SupplierMapper;
import project.spring.quanlysach.application.repo.SupplierRepository;
import project.spring.quanlysach.application.services.ISupplierService;
import project.spring.quanlysach.config.exception.VsException;
import project.spring.quanlysach.domain.dto.SupplierDTO;
import project.spring.quanlysach.domain.entity.Supplier;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ImpSupplierService implements ISupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper = Mappers.getMapper(SupplierMapper.class);

    public ImpSupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public List<Supplier> showAllListSupplier(Integer page, Integer size) {
        List<Supplier> listSupplier;
        if (page != null) {
            listSupplier = supplierRepository.findAll(PageRequest.of(page.intValue(), size)).getContent();
        } else {
            listSupplier = supplierRepository.findAll();
        }
        return listSupplier;
    }

    @Override
    public Supplier getSupplierById(int id) {
        Optional<Supplier> foundSupplier = supplierRepository.findById(id);
        if (foundSupplier.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "Supplier", id));
        }
        return foundSupplier.get();
    }

    @Override
    public Supplier createNewSupplier(SupplierDTO supplierDTO) {

        Supplier foundSupplierByName = supplierRepository.findSupplierByName(supplierDTO.getName());
        Supplier findSupplierByPhone = supplierRepository.findSupplierByPhone(supplierDTO.getPhone());
        if (foundSupplierByName == null && findSupplierByPhone == null) {
            Supplier supplier = supplierMapper.toSupplier(supplierDTO);
            return supplierRepository.save(supplier);
        } else {
            if (findSupplierByPhone != null && foundSupplierByName != null) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.OBJECT_IS_EXITS));
            } else if (foundSupplierByName != null) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.EXITS_NAME, supplierDTO.getName()));
            } else {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.EXITS_NAME, supplierDTO.getPhone()));
            }
        }
    }

    @Override
    public Supplier searchSupplierByName(String name) {
        Supplier supplier = supplierRepository.findSupplierByName("%" + name.trim().toLowerCase() + "%");
        if (supplier != null) {
            return supplier;
        } else {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.OBJECT_NOT_FOUND));
        }
    }

    @Override
    public List<Supplier> searchSupplierByAddress(String address) {
        List<Supplier> supplier = supplierRepository.findSupplierByAddress("%" + address.trim().toLowerCase() + "%");
        if (supplier != null) {
            return supplier;
        } else {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.OBJECT_NOT_FOUND));
        }
    }

    @Override
    public String updateInfoSupplier(SupplierDTO supplierDTO, int id) {
        Optional<Supplier> findSupplier = supplierRepository.findById(id);

        if (findSupplier.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "supplier", id));
        }
        Supplier foundSupplierByName = supplierRepository.findSupplierByName(supplierDTO.getName());
        Supplier findSupplierByPhone = supplierRepository.findSupplierByPhone(supplierDTO.getPhone());
        if ((foundSupplierByName == null || Objects.equals(findSupplier.get().getName(), supplierDTO.getName()))
                && (findSupplierByPhone == null || Objects.equals(findSupplier.get().getPhone(), supplierDTO.getPhone()))) {
            findSupplier = Optional.ofNullable(supplierMapper.toSupplier(supplierDTO));
            findSupplier.get().setId(id);
            supplierRepository.save(findSupplier.get());
            return DevMessageConstant.Common.NOTIFICATION_UPDATE_SUCCESS;
        } else {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.OBJECT_IS_EXITS));
        }
    }

    @Override
    public String deleteSupplier(int id) {
        Optional<Supplier> foundSupplier = supplierRepository.findById(id);
        if (foundSupplier.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "Supplier", id));
        }
        supplierRepository.deleteById(id);
        return DevMessageConstant.Common.NOTIFICATION_DELETE_SUCCESS;
    }
}
