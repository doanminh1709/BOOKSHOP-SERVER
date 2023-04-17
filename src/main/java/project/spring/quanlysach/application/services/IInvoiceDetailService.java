package project.spring.quanlysach.application.services;

import project.spring.quanlysach.domain.dto.DeleteInvoiceDetail;
import project.spring.quanlysach.domain.dto.InvoiceDetailDTO;
import project.spring.quanlysach.domain.entity.InvoiceDetail;

import java.util.List;

public interface IInvoiceDetailService {

    InvoiceDetail createNewInvoice(InvoiceDetailDTO invoiceDetailDTO);

    List<InvoiceDetail> listInvoice(Integer page, Integer size);

    String updateInfoInvoiceDetail(InvoiceDetailDTO invoiceDetailDTO);

    String deleteInvoiceDetail(DeleteInvoiceDetail invoiceDetail);

    List<InvoiceDetail> searchByInvoiceDetailByProductName(String productName);

    List<InvoiceDetail> sortInvoiceDetailASCByPrice(Integer page, Integer size);

    List<InvoiceDetail> show3ProductAlreadyTheMost();
}
