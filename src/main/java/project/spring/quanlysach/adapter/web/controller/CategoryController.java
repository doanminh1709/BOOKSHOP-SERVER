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
import project.spring.quanlysach.application.services.ICategoryService;
import project.spring.quanlysach.application.utils.UploadFileCloudinary;
import project.spring.quanlysach.domain.dto.CategoryDTO;

import java.io.IOException;
import java.text.ParseException;

@RestApiV0
public class CategoryController {

    @Autowired
    private ICategoryService iCategoryService;
    @Autowired
    private ReadFileExcel fileExcel;

    @Autowired
    private UploadFileCloudinary uploadFile;

    @GetMapping(UrlConstant.Category.DATA_CATEGORY)
    @ApiOperation(value = "Get find all category")
    public ResponseEntity<?> getFindAllCategory(@RequestParam(name = "page", required = false) Integer page) {
        return VsResponseUtil.ok(iCategoryService.findAllListCategory(page, CommonConstant.SIZE_OFF_PAGE));
    }

    @PostMapping(UrlConstant.Category.CREATE_CATEGORY)
    @ApiOperation(value = "Create new category")
    public ResponseEntity<?> createNewCategory(@RequestBody CategoryDTO categoryDTO) {
        return VsResponseUtil.ok(iCategoryService.createNewCategory(categoryDTO));
    }

    @PutMapping(UrlConstant.Category.UPDATE_CATEGORY)
    @ApiOperation(value = "Update old category")
    public ResponseEntity<?> updateOldCategory(@PathVariable("id") Integer id, @RequestBody CategoryDTO categoryDTO) {
        return VsResponseUtil.ok(iCategoryService.updateCategoryById(categoryDTO, id));
    }

    @GetMapping(UrlConstant.Category.GET_CATEGORY)
    @ApiOperation(value = "Get category by id")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") Integer id) {
        return VsResponseUtil.ok(iCategoryService.getCategoryById(id));
    }

    @DeleteMapping(UrlConstant.Category.DELETE_CATEGORY)
    @ApiOperation(value = "Upset active category by id")
    public ResponseEntity<?> upsetActiveCategoryById(@PathVariable("id") Integer id) {
        return VsResponseUtil.ok(iCategoryService.deleteCategoryById(id));
    }

    @PostMapping(UrlConstant.Category.UPLOAD_CATEGORY_DATA_FROM_EXCEL)
    @ApiOperation("Upload data category from excel file to database")
    public ResponseEntity<?> uploadFileFileToData(@RequestParam("file") MultipartFile multipartFile) throws IOException, ParseException {
        return VsResponseUtil.ok(fileExcel.saveCategoryData(uploadFile.convertMultipartToFile(multipartFile)));
    }
}
