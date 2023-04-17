package project.spring.quanlysach.adapter.web.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.spring.quanlysach.adapter.web.base.RestApiV0;
import project.spring.quanlysach.adapter.web.base.VsResponseUtil;
import project.spring.quanlysach.application.constants.CommonConstant;
import project.spring.quanlysach.application.constants.UrlConstant;
import project.spring.quanlysach.application.services.IInvoiceDetailService;
import project.spring.quanlysach.domain.dto.DeleteInvoiceDetail;
import project.spring.quanlysach.domain.dto.InvoiceDetailDTO;

import javax.validation.Valid;

@RestApiV0
public class InvoiceDetailController {

    @Autowired
    private IInvoiceDetailService iInvoiceDetailService;

    @PostMapping(UrlConstant.InvoiceDetail.CREATE_INVOICE_DETAIL)
    @ApiOperation("Create new invoice")
    public ResponseEntity<?> createNewInvoice(@RequestBody @Valid InvoiceDetailDTO invoiceDetailDTO) {
        return VsResponseUtil.ok(iInvoiceDetailService.createNewInvoice(invoiceDetailDTO));
    }

    @GetMapping(UrlConstant.InvoiceDetail.SHOW_ALL_INVOICE_DETAIL)
    @ApiOperation("Show all invoice detail")
    public ResponseEntity<?> showAllInvoiceDetail(@RequestParam(value = "page", required = false) Integer page) {
        return VsResponseUtil.ok(iInvoiceDetailService.listInvoice(page, CommonConstant.SIZE_OFF_PAGE));
    }


    @PutMapping(UrlConstant.InvoiceDetail.UPDATE_INVOICE_DETAIL)
    @ApiOperation("Show all invoice detail")
    public ResponseEntity<?> showAllInvoiceDetail(@RequestBody InvoiceDetailDTO invoiceDetailDTO) {
        return VsResponseUtil.ok(iInvoiceDetailService.updateInfoInvoiceDetail(invoiceDetailDTO));
    }


    @DeleteMapping(UrlConstant.InvoiceDetail.DELETE_INVOICE_DETAIL)
    @ApiOperation("Delete invoice detail")
    public ResponseEntity<?> deleteInvoiceDetail(@RequestBody DeleteInvoiceDetail deleteInvoiceDetail) {
        return VsResponseUtil.ok(iInvoiceDetailService.deleteInvoiceDetail(deleteInvoiceDetail));
    }

    @GetMapping(UrlConstant.InvoiceDetail.SEARCH_INVOICE_DETAIL_BY_NAME)
    @ApiOperation("Search invoice detail by name")
    public ResponseEntity<?> searchInvoiceDetailByName(@RequestParam(value = "name", required = false) String name) {
        return VsResponseUtil.ok(iInvoiceDetailService.searchByInvoiceDetailByProductName(name));
    }

    @GetMapping(UrlConstant.InvoiceDetail.SORT_INVOICE_DETAIL_DESC)
    @ApiOperation("Show list invoice detail after sorted asc by price")
    public ResponseEntity<?> sortInvoiceDetailASCByPrice(@RequestParam(value = "page", required = false) Integer page) {
        return VsResponseUtil.ok(iInvoiceDetailService.sortInvoiceDetailASCByPrice(page, CommonConstant.SIZE_OFF_PAGE));
    }

    @GetMapping(UrlConstant.InvoiceDetail.SHOW3_PRODUCT_ALREADY_THE_MOST)
    @ApiOperation("Show 3 product already the most")
    public ResponseEntity<?> show3ProductAlreadyTheMost() {
        return VsResponseUtil.ok(iInvoiceDetailService.show3ProductAlreadyTheMost());
    }

}
