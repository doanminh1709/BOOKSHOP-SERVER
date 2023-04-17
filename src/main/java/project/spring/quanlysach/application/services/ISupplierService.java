package project.spring.quanlysach.application.services;

import project.spring.quanlysach.domain.dto.SupplierDTO;
import project.spring.quanlysach.domain.entity.Supplier;

import java.util.List;

public interface ISupplierService {
    List<Supplier> showAllListSupplier(Integer page , Integer size);

    Supplier getSupplierById(int id);

    Supplier createNewSupplier(SupplierDTO supplierDTO);

    Supplier searchSupplierByName(String name);

    List<Supplier> searchSupplierByAddress(String address);

    String updateInfoSupplier(SupplierDTO supplierDTO, int id);

    String deleteSupplier(int id);
}
