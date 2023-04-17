package project.spring.quanlysach.adapter.web.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.spring.quanlysach.adapter.web.base.RestApiV0;
import project.spring.quanlysach.adapter.web.base.VsResponseUtil;
import project.spring.quanlysach.application.constants.CommonConstant;
import project.spring.quanlysach.application.constants.UrlConstant;
import project.spring.quanlysach.application.excel.ReadFileExcel;
import project.spring.quanlysach.application.services.IBrandService;
import project.spring.quanlysach.application.utils.UploadFileCloudinary;
import project.spring.quanlysach.domain.dto.BrandDTO;

import javax.validation.Valid;
import java.io.IOException;

@RestApiV0
public class BrandController {

    @Autowired
    private IBrandService iBrandService;
    @Autowired
    private ReadFileExcel fileExcel;

    @Autowired
    private UploadFileCloudinary uploadFile;

    @GetMapping(UrlConstant.Brand.DATA_BRAND)
    @ApiOperation(value = "Get all brand")
    public ResponseEntity<?> getFindAllBrand(@RequestParam(name = "page", required = false) Integer page) {
        return VsResponseUtil.ok(iBrandService.listBrand(page, CommonConstant.SIZE_OFF_PAGE));
    }

    @PostMapping(UrlConstant.Brand.CREATE_BRAND)
    @ApiOperation(value = "Create new brand")
    public ResponseEntity<?> createNewBrand(@RequestBody @Valid BrandDTO brandDTO) {
        return VsResponseUtil.ok(iBrandService.createNewBrand(brandDTO));
    }

    @PutMapping(UrlConstant.Brand.UPDATE_BRAND)
    @ApiOperation(value = "Update old brand by id")
    public ResponseEntity<?> updateOldBrand(@PathVariable("id") Integer id, @RequestBody BrandDTO brandDTO) {
        return VsResponseUtil.ok(iBrandService.updateBrandById(brandDTO, id));
    }

    @GetMapping(UrlConstant.Brand.GET_BRAND)
    @ApiOperation(value = "Get brand by id")
    public ResponseEntity<?> getBrandById(@PathVariable("id") Integer id) {
        return VsResponseUtil.ok(iBrandService.getBrandById(id));
    }

    @DeleteMapping(UrlConstant.Brand.DELETE_BRAND)
    @ApiOperation(value = "Upset active brand by id")
    public ResponseEntity<?> upsetActiveBrandById(@PathVariable("id") Integer id) {
        return VsResponseUtil.ok(iBrandService.deleteBrandById(id));
    }

    @PostMapping(UrlConstant.Brand.UPLOAD_BRAND_DATA_FROM_EXCEL)
    @ApiOperation("Upload data brand from excel file to database")
    public ResponseEntity<?> uploadFileFileToData(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        return VsResponseUtil.ok(fileExcel.saveBrandData(uploadFile.convertMultipartToFile(multipartFile)));
    }
}
