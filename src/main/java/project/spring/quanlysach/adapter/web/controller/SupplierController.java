package project.spring.quanlysach.adapter.web.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.spring.quanlysach.adapter.web.base.RestApiV0;
import project.spring.quanlysach.adapter.web.base.VsResponseUtil;
import project.spring.quanlysach.application.constants.CommonConstant;
import project.spring.quanlysach.application.constants.UrlConstant;
import project.spring.quanlysach.application.excel.ReadFileExcel;
import project.spring.quanlysach.application.services.ISupplierService;
import project.spring.quanlysach.application.utils.UploadFileCloudinary;
import project.spring.quanlysach.domain.dto.SupplierDTO;

import javax.validation.Valid;
import java.io.IOException;

@RestApiV0
public class SupplierController {

    @Autowired
    private ISupplierService iSupplierService;

    @Autowired
    private ReadFileExcel fileExcel;

    @Autowired
    private UploadFileCloudinary uploadFile;

    @GetMapping(UrlConstant.Supplier.GET_ALL_SUPPLIER)
    @ApiOperation("Show information about all supplier")
    public ResponseEntity<?> findAllSupplier(@RequestParam(value = "page", required = false) Integer page) {
        return VsResponseUtil.ok(iSupplierService.showAllListSupplier(page, CommonConstant.SIZE_OFF_PAGE));
    }

    @GetMapping(UrlConstant.Supplier.GET_SUPPLIER_BY_ID)
    @ApiOperation("Show information about supplier by supplier id")
    public ResponseEntity<?> findSupplierById(@PathVariable("id") int id) {
        return VsResponseUtil.ok(iSupplierService.getSupplierById(id));
    }

    @PostMapping(UrlConstant.Supplier.CREATE_NEW_SUPPLIER)
    @ApiOperation("Create new supplier")
    public ResponseEntity<?> newSupplier(@RequestBody @Valid SupplierDTO supplierDTO) {
        return VsResponseUtil.ok(iSupplierService.createNewSupplier(supplierDTO));
    }

    @GetMapping(UrlConstant.Supplier.SEARCH_SUPPLIER_BY_NAME)
    @ApiOperation("Search supplier by supplier name")
    public ResponseEntity<?> searchSupplierByName(@RequestParam(value = "name", required = false) String name) {
        return VsResponseUtil.ok(iSupplierService.searchSupplierByName(name));
    }

    @GetMapping(UrlConstant.Supplier.SEARCH_SUPPLIER_BY_ADDRESS)
    @ApiOperation("Search supplier by supplier address")
    public ResponseEntity<?> findSupplierById(@RequestParam(value = "address", required = false) String address) {
        return VsResponseUtil.ok(iSupplierService.searchSupplierByAddress(address));
    }

    //chua duoc
    @PutMapping(UrlConstant.Supplier.UPDATE_SUPPLIER_BY_ID)
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation("Update supplier by supplier id")
    public ResponseEntity<?> updateSupplierById(@RequestBody SupplierDTO supplierDTO, @PathVariable("id") int id) {
        return VsResponseUtil.ok(iSupplierService.updateInfoSupplier(supplierDTO, id));
    }

    @DeleteMapping(UrlConstant.Supplier.DELETE_SUPPLIER_BY_ID)
    @ApiOperation("Delete supplier by supplier id")
    public ResponseEntity<?> deleteSupplierById(@PathVariable("id") int id) {
        return VsResponseUtil.ok(iSupplierService.deleteSupplier(id));
    }

    @PostMapping(UrlConstant.Supplier.READ_DATA_ON_EXCEL_FILE)
    @ApiOperation("Read data from excel file ")
    public ResponseEntity<?> uploadDataFromExcelFileToData(@RequestParam("file") MultipartFile multipartFile)
            throws IOException {
        return VsResponseUtil.ok(fileExcel.saveSupplier(uploadFile.convertMultipartToFile(multipartFile)));
    }
}
