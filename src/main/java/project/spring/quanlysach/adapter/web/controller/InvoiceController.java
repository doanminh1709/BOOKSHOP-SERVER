package project.spring.quanlysach.adapter.web.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.spring.quanlysach.adapter.web.base.RestApiV0;
import project.spring.quanlysach.adapter.web.base.VsResponseUtil;
import project.spring.quanlysach.application.constants.CommonConstant;
import project.spring.quanlysach.application.constants.UrlConstant;
import project.spring.quanlysach.application.services.IInvoiceService;
import project.spring.quanlysach.domain.dto.InvoiceDTO;

import javax.validation.Valid;

@RestApiV0
public class InvoiceController {
    @Autowired
    private IInvoiceService invoiceService;

    @GetMapping(UrlConstant.Invoice.SHOW_INVOICE_LEAST_TIME)
    @ApiOperation("Show all invoice by least time")
    public ResponseEntity<?> showAllInvoiceByLeastTime(@RequestParam(name = "page", required = false) Integer page) {
        return VsResponseUtil.ok(invoiceService.showListInvoiceLeastTime(page, CommonConstant.SIZE_OFF_PAGE));
    }

    @GetMapping(UrlConstant.Invoice.SHOW_ALL_INVOICE)
    @ApiOperation("Show all invoice")
    public ResponseEntity<?> showAllInvoice(@RequestParam(name = "page", required = false) Integer page) {
        return VsResponseUtil.ok(invoiceService.showAllListInvoice(page, CommonConstant.SIZE_OFF_PAGE));
    }

    @GetMapping(UrlConstant.Invoice.SEARCH_BY_STATUS)
    @ApiOperation("Search invoice by status")
    public ResponseEntity<?> searchByStatus(@RequestParam(value = "status", required = false) String status) {
        return VsResponseUtil.ok(invoiceService.getInvoiceByStatus(status));
    }

    @GetMapping(UrlConstant.Invoice.SEARCH_BY_SUPPLIER_ID)
    @ApiOperation("Search invoice by supplier id")
    public ResponseEntity<?> searchInvoiceBySupplierId(@PathVariable("supplierId") int supplierId) {
        return VsResponseUtil.ok(invoiceService.searchInvoiceBySupplier(supplierId));
    }

    @PostMapping(UrlConstant.Invoice.NEW_INVOICE)
    @ApiOperation("Create new invoice")
    public ResponseEntity<?> createNewInvoice(@RequestBody @Valid InvoiceDTO invoiceDTO) {
        return VsResponseUtil.ok(invoiceService.createNewInvoice(invoiceDTO));
    }

    @GetMapping(UrlConstant.Invoice.GET_INVOICE_BY_ID)
    @ApiOperation("Get invoice by id")
    public ResponseEntity<?> getInvoiceById(@PathVariable("id") Integer id) {
        return VsResponseUtil.ok(invoiceService.getInvoiceById(id));
    }

    @PutMapping(UrlConstant.Invoice.UPDATE_INVOICE_BY_ID)
    @ApiOperation("Update invoice by invoice id")
    public ResponseEntity<?> updateInvoice(@PathVariable("id") int id, @RequestBody InvoiceDTO invoiceDTO) {
        return VsResponseUtil.ok(invoiceService.updateInvoice(invoiceDTO, id));
    }

    @DeleteMapping(UrlConstant.Invoice.REMOVE_INVOICE_BY_ID)
    @ApiOperation("Remove invoice by invoice id")
    public ResponseEntity<?> removeInvoiceById(@PathVariable("id") int id) {
        return VsResponseUtil.ok(invoiceService.removeInvoice(id));
    }
}
