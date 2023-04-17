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
import project.spring.quanlysach.application.services.IProductService;
import project.spring.quanlysach.application.utils.UploadFileCloudinary;
import project.spring.quanlysach.domain.dto.ProductDTO;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;

@RestApiV0
public class ProductController {

    @Autowired
    private IProductService iProductService;

    @Autowired
    private ReadFileExcel fileExcel;

    @Autowired
    private UploadFileCloudinary uploadFile;

    @GetMapping(UrlConstant.Product.GET_ALL_PRODUCT)
    @ApiOperation("Get all product")
    public ResponseEntity<?> getAllProduct(@RequestParam(value = "page", required = false) Integer page) {
        return VsResponseUtil.ok(iProductService.findAllListProduct(page, CommonConstant.SIZE_OFF_PAGE));
    }

    @PostMapping(UrlConstant.Product.CREATE_PRODUCT)
    @ApiOperation("Create new product")
    public ResponseEntity<?> createNewProduct(@RequestBody @Valid ProductDTO productDTO) {
        return VsResponseUtil.ok(iProductService.createNewProduct(productDTO));
    }

    @PutMapping(UrlConstant.Product.UPDATE_PRODUCT)
    @ApiOperation("Update information of product")
    public ResponseEntity<?> updateProduct(@RequestBody @Valid ProductDTO productDTO,
                                           @PathVariable("id") int id) {
        return VsResponseUtil.ok(iProductService.updateProductById(productDTO, id));
    }

    @GetMapping(UrlConstant.Product.GET_PRODUCT_BY_ID)
    @ApiOperation("Search information product by product id")
    public ResponseEntity<?> getProductById(@PathVariable("id") int id) {
        return VsResponseUtil.ok(iProductService.getProductById(id));
    }

    @DeleteMapping(UrlConstant.Product.REMOVE_PRODUCT)
    @ApiOperation("Delete information of product by product id")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") int id) {
        return VsResponseUtil.ok(iProductService.deleteProductById(id));
    }

    @GetMapping(UrlConstant.Product.SEARCH_PRODUCT_BY_NAME)
    @ApiOperation("Search product by product name")
    public ResponseEntity<?> searchProductByName(@RequestParam(name = "name", required = false) String name) {
        return VsResponseUtil.ok(iProductService.searchByName(name));
    }

    @GetMapping(UrlConstant.Product.SEARCH_PRODUCT_BY_STATUS)
    @ApiOperation("Search product by status")
    public ResponseEntity<?> showProductByStatus(@RequestParam(name = "status", required = false) String status) {
        return VsResponseUtil.ok(iProductService.showListProductByStatus(status));
    }

    @GetMapping(UrlConstant.Product.GET_ALL_PRODUCT_BY_CATEGORY)
    @ApiOperation("Get all product by category id")
    public ResponseEntity<?> showListProductByCategory(@PathVariable("categoryId") int categoryId) {
        return VsResponseUtil.ok(iProductService.showListProductByCategory(categoryId));
    }

    @GetMapping(UrlConstant.Product.GET_ALL_PRODUCT_BY_BRAND)
    @ApiOperation("Get all product by brand id")
    public ResponseEntity<?> showListProductByBrand(@PathVariable("brandId") int brandId) {
        return VsResponseUtil.ok(iProductService.showListProductByBrand(brandId));
    }

    @GetMapping(UrlConstant.Product.GET_ALL_PRODUCT_ASC)
    @ApiOperation("Show list product sorted asc by price")
    public ResponseEntity<?> showListProductSortASC(@RequestParam(value = "page", required = false) Integer page) {
        return VsResponseUtil.ok(iProductService.softProductASCByPrice(page, CommonConstant.SIZE_OFF_PAGE));
    }

    @GetMapping(UrlConstant.Product.GET_ALL_PRODUCT_DESC)
    @ApiOperation("Show list product sorted desc by price")
    public ResponseEntity<?> showListProductSortDESC(@RequestParam(value = "page", required = false) Integer page) {
        return VsResponseUtil.ok(iProductService.softProductDESCByPrice(page, CommonConstant.SIZE_OFF_PAGE));
    }

    @GetMapping(UrlConstant.Product.GET_PRODUCT_BETWEEN_PRICE)
    @ApiOperation("Search product between money")
    public ResponseEntity<?> searchProductBetweenMoney(@RequestParam(value = "start_point", required = false) Float start,
                                                       @RequestParam(value = "end_point", required = false) Float end) {
        return VsResponseUtil.ok(iProductService.searchProductBetweenMoney(start, end));
    }

    @PostMapping(UrlConstant.Product.READ_DATA_ON_EXCEL_FILE)
//    @PreAuthorize("hasAnyRole('ADMIN' , 'MANAGE')")
    @ApiOperation("Upload data product from excel file to database")
    public ResponseEntity<?> uploadDataFromExcelFileToData(@RequestParam("file") MultipartFile multipartFile) throws IOException
            , ParseException, IOException {
        return VsResponseUtil.ok(fileExcel.saveProductData(uploadFile.convertMultipartToFile(multipartFile)));
    }
}
