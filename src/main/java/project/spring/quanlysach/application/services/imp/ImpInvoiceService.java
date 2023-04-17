package project.spring.quanlysach.application.services.imp;

import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import project.spring.quanlysach.application.constants.DevMessageConstant;
import project.spring.quanlysach.application.constants.UserMessConstant;
import project.spring.quanlysach.application.mapper.InvoiceMapper;
import project.spring.quanlysach.application.repo.InvoiceRepository;
import project.spring.quanlysach.application.repo.SupplierRepository;
import project.spring.quanlysach.application.services.IInvoiceService;
import project.spring.quanlysach.config.exception.VsException;
import project.spring.quanlysach.domain.dto.InvoiceDTO;
import project.spring.quanlysach.domain.entity.Invoice;
import project.spring.quanlysach.domain.entity.Supplier;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImpInvoiceService implements IInvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final SupplierRepository supplierRepository;
    private final InvoiceMapper invoiceMapper = Mappers.getMapper(InvoiceMapper.class);

    public ImpInvoiceService(InvoiceRepository invoiceRepository, SupplierRepository supplierRepository) {
        this.invoiceRepository = invoiceRepository;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Invoice createNewInvoice(InvoiceDTO invoiceDTO) {
        Optional<Supplier> supplier = supplierRepository.findById(invoiceDTO.getSupplierId());
        if (supplier.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "supplier", invoiceDTO.getSupplierId()));
        }
        return invoiceRepository.save(invoiceMapper.toInvoice(invoiceDTO));
    }

    @Override
    public List<Invoice> showAllListInvoice(Integer page, Integer size) {
        List<Invoice> listInvoice;
        if (page != null) {
            listInvoice = invoiceRepository.findAll(PageRequest.of(page.intValue(), size)).getContent();
        } else {
            listInvoice = invoiceRepository.findAll();
        }
        return listInvoice;
    }

    @Override
    public List<Invoice> getInvoiceByStatus(String status) {
        if (status == null) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.EMPTY));
        }
        return invoiceRepository.searchInvoiceByStatus("%" + status.trim() + "%");
    }

    @Override
    public List<Invoice> showListInvoiceLeastTime(Integer page, Integer size) {
        List<Invoice> listInvoice;
        if (page != null) {
            listInvoice = invoiceRepository.findAll(PageRequest.of(page.intValue(), size)).getContent()
                    .stream().sorted(Comparator.comparing(Invoice::getCreateDate).reversed())
                    .collect(Collectors.toList());
        } else {
            listInvoice = invoiceRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate"));
        }
        return listInvoice;
    }

    @Override
    public List<Invoice> searchInvoiceBySupplier(int supplierId) {
        Optional<Supplier> supplier = supplierRepository.findById(supplierId);
        if (supplier.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "supplier", supplierId));
        }
        return invoiceRepository.findInvoiceBySupplierId(supplierId);
    }

    @Override
    public Invoice getInvoiceById(int id) {
        Optional<Invoice> foundInvoice = invoiceRepository.findById(id);
        if (foundInvoice.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "Invoice", id));
        }
        return foundInvoice.get();
    }

    @Override
    public String updateInvoice(InvoiceDTO invoiceDTO, int id) {
        Optional<Invoice> foundInvoice = invoiceRepository.findById(id);
        if (foundInvoice.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "Invoice", id));
        }
        foundInvoice = Optional.ofNullable(invoiceMapper.toInvoice(invoiceDTO));
        foundInvoice.get().setId(id);
        invoiceRepository.save(foundInvoice.get());
        return DevMessageConstant.Common.NOTIFICATION_UPDATE_SUCCESS;
    }

    @Override
    public String removeInvoice(int id) {
        Optional<Invoice> foundInvoice = invoiceRepository.findById(id);
        if (foundInvoice.isPresent()) {
            invoiceRepository.delete(foundInvoice.get());
            return DevMessageConstant.Common.NOTIFICATION_DELETE_SUCCESS;
        }
        throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "Invoice", id));

    }
}
