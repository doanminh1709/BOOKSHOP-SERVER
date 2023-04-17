package project.spring.quanlysach.application.services.imp;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import project.spring.quanlysach.application.constants.DevMessageConstant;
import project.spring.quanlysach.application.constants.UserMessConstant;
import project.spring.quanlysach.application.mapper.InvoiceDetailMapper;
import project.spring.quanlysach.application.repo.InvoiceDetailRepository;
import project.spring.quanlysach.application.repo.InvoiceRepository;
import project.spring.quanlysach.application.repo.ProductRepository;
import project.spring.quanlysach.application.services.IInvoiceDetailService;
import project.spring.quanlysach.config.exception.VsException;
import project.spring.quanlysach.domain.dto.DeleteInvoiceDetail;
import project.spring.quanlysach.domain.dto.InvoiceDetailDTO;
import project.spring.quanlysach.domain.entity.Invoice;
import project.spring.quanlysach.domain.entity.InvoiceDetail;
import project.spring.quanlysach.domain.entity.InvoiceDetailId;
import project.spring.quanlysach.domain.entity.Product;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class ImpInvoiceDetailService implements IInvoiceDetailService {
    private final InvoiceDetailRepository invoiceDetailRepository;
    private final ProductRepository productRepository;
    private final InvoiceRepository invoiceRepository;
    private final InvoiceDetailMapper detailMapper = Mappers.getMapper(InvoiceDetailMapper.class);

    public ImpInvoiceDetailService(InvoiceDetailRepository invoiceDetailRepository, ProductRepository productRepository, InvoiceRepository invoiceRepository) {
        this.invoiceDetailRepository = invoiceDetailRepository;
        this.productRepository = productRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public InvoiceDetail createNewInvoice(InvoiceDetailDTO invoiceDetailDTO) {

        Optional<Product> foundProduct = productRepository.findById(invoiceDetailDTO.getProductId());
        Optional<Invoice> foundInvoice = invoiceRepository.findById(invoiceDetailDTO.getInvoiceId());
        if (foundInvoice.isPresent() && foundProduct.isPresent()) {
            InvoiceDetail foundInvoiceDetail = invoiceDetailRepository.findInvoiceDetailByProductIdAndInvoiceId(
                    invoiceDetailDTO.getProductId(),
                    invoiceDetailDTO.getInvoiceId());
            if (foundInvoiceDetail != null) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.OBJECT_IS_EXITS));
            } else {
                InvoiceDetail invoiceDetail = detailMapper.toInvoiceDetail(invoiceDetailDTO);
                InvoiceDetailId invoiceDetailId = new InvoiceDetailId();

                invoiceDetailId.setInvoice(foundInvoice.get());
                invoiceDetailId.setProduct(foundProduct.get());
                invoiceDetail.setInvoiceDetailId(invoiceDetailId);

                return invoiceDetailRepository.save(invoiceDetail);
            }
        } else {
            if (foundInvoice.isEmpty() && foundProduct.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.OBJECT_NOT_FOUND));
            } else if (foundInvoice.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "Invoice",
                                invoiceDetailDTO.getInvoiceId()));
            } else {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "Product",
                                invoiceDetailDTO.getProductId()));
            }
        }
    }

    @Override
    public List<InvoiceDetail> listInvoice(Integer page, Integer size) {
        List<InvoiceDetail> invoiceDetailList;
        if (page != null) {
            invoiceDetailList = invoiceDetailRepository.findAll(PageRequest.of(page.intValue(), size)).getContent();
        } else {
            invoiceDetailList = invoiceDetailRepository.findAll();
        }
        return invoiceDetailList;
    }

    @Override
    public String updateInfoInvoiceDetail(InvoiceDetailDTO invoiceDetailDTO) {

        Optional<Product> foundProduct = productRepository.findById(invoiceDetailDTO.getProductId());
        Optional<Invoice> foundInvoice = invoiceRepository.findById(invoiceDetailDTO.getInvoiceId());
        if (foundInvoice.isPresent() && foundProduct.isPresent()) {
            InvoiceDetail foundInvoiceDetail = invoiceDetailRepository.findInvoiceDetailByProductIdAndInvoiceId(
                    invoiceDetailDTO.getProductId(),
                    invoiceDetailDTO.getInvoiceId());
            if (foundInvoiceDetail == null) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.OBJECT_IS_EXITS));
            } else {
                InvoiceDetailId invoiceDetailId = foundInvoiceDetail.getInvoiceDetailId();
                foundInvoiceDetail = detailMapper.toInvoiceDetail(invoiceDetailDTO);
                foundInvoiceDetail.setInvoiceDetailId(invoiceDetailId);
                invoiceDetailRepository.save(foundInvoiceDetail);

                return DevMessageConstant.Common.NOTIFICATION_UPDATE_SUCCESS;
            }
        } else {
            if (foundInvoice.isEmpty() && foundProduct.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.OBJECT_NOT_FOUND));
            } else if (foundInvoice.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "Invoice",
                                invoiceDetailDTO.getInvoiceId()));
            } else {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "Product",
                                invoiceDetailDTO.getProductId()));
            }
        }
    }

    @Override
    public String deleteInvoiceDetail(DeleteInvoiceDetail invoiceDetail) {

        int productId = invoiceDetail.getInvoiceDetail()[0];
        int invoiceId = invoiceDetail.getInvoiceDetail()[1];
        Optional<Product> foundProduct = productRepository.findById(productId);
        Optional<Invoice> foundInvoice = invoiceRepository.findById(invoiceId);
        if (foundInvoice.isPresent() && foundProduct.isPresent()) {
            InvoiceDetail foundInvoiceDetail = invoiceDetailRepository.findInvoiceDetailByProductIdAndInvoiceId(
                    invoiceDetail.getInvoiceDetail()[0], invoiceDetail.getInvoiceDetail()[1]);
            if (foundInvoiceDetail == null) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.OBJECT_IS_EXITS));
            } else {
                invoiceDetailRepository.deleteInvoiceDetailByProductIdAndInvoiceId(productId, invoiceId);
                return DevMessageConstant.Common.NOTIFICATION_DELETE_SUCCESS;
            }
        } else {
            if (foundInvoice.isEmpty() && foundProduct.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.OBJECT_NOT_FOUND));
            } else if (foundProduct.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "Product",
                                productId));
            } else {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "Invoice",
                                invoiceId));
            }
        }
    }

    @Override
    public List<InvoiceDetail> searchByInvoiceDetailByProductName(String productName) {

        List<InvoiceDetail> invoiceDetail = invoiceDetailRepository.searchByName("%" + productName.trim() + "%");
        if (invoiceDetail.size() > 0) {
            return invoiceDetail;
        }
        throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                DevMessageConstant.Common.NO_DATA_SELECTED);
    }

    @Override
    public List<InvoiceDetail> sortInvoiceDetailASCByPrice(Integer page, Integer size) {
        if (page != null) {
            return invoiceDetailRepository.findAll(PageRequest.of(page.intValue(), size)).getContent()
                    .stream().sorted(Comparator.comparing(InvoiceDetail::getPrice)).collect(Collectors.toList());
        } else {
            return invoiceDetailRepository.findAll(Sort.by(Sort.Direction.DESC , "price"));
        }
    }

    @Override
    public List<InvoiceDetail> show3ProductAlreadyTheMost() {

//        List<InvoiceDetail> invoiceDetail = invoiceDetailRepository.show3Product();
//        if (invoiceDetail.size() > 0) {
//            return invoiceDetail;
//        }
//        throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
//                DevMessageConstant.Common.NO_DATA_SELECTED);
        return new ArrayList<>();
    }
}
