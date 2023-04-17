package project.spring.quanlysach.application.services;

import project.spring.quanlysach.domain.dto.InvoiceDTO;
import project.spring.quanlysach.domain.entity.Invoice;

import java.util.List;

public interface IInvoiceService {

    Invoice createNewInvoice(InvoiceDTO invoiceDTO);

    List<Invoice> showAllListInvoice(Integer page, Integer size);

    List<Invoice> getInvoiceByStatus(String status);

    List<Invoice> showListInvoiceLeastTime(Integer page, Integer size);

    List<Invoice> searchInvoiceBySupplier(int supplierId);

    Invoice getInvoiceById(int id);

    String updateInvoice(InvoiceDTO invoiceDTO, int id);

    String removeInvoice(int id);
}
