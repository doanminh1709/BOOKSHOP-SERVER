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
import project.spring.quanlysach.application.services.ICateService;
import project.spring.quanlysach.application.utils.UploadFileCloudinary;
import project.spring.quanlysach.domain.dto.CateDTO;

import java.io.IOException;
import java.text.ParseException;

@RestApiV0
@ApiOperation(value = "List api of cate")
public class CateController {

    @Autowired
    private ICateService iCateService;
    @Autowired
    private ReadFileExcel fileExcel;

    @Autowired
    private UploadFileCloudinary uploadFile;

    @GetMapping(UrlConstant.Cate.DATA_CATE)
    @ApiOperation(value = "Get find all category")
    public ResponseEntity<?> getFindAllCategory(@RequestParam(name = "page", required = false) Integer page) {
        return VsResponseUtil.ok(iCateService.listCate(page, CommonConstant.SIZE_OFF_PAGE));
    }

    @PostMapping(UrlConstant.Cate.CREATE_CATE)
    @ApiOperation(value = "Create new category category")
    public ResponseEntity<?> createNewCategory(@RequestBody CateDTO cateDTO) {
        return VsResponseUtil.ok(iCateService.createNewCate(cateDTO));
    }

    @PutMapping(UrlConstant.Cate.UPDATE_CATE)
    @ApiOperation(value = "Update old category")
    public ResponseEntity<?> updateOldCategory(@PathVariable("id") Integer id, @RequestBody CateDTO cateDTO) {
        return VsResponseUtil.ok(iCateService.updateCateById(cateDTO, id));
    }

    @GetMapping(UrlConstant.Cate.GET_CATE)
    @ApiOperation(value = "Get category by id")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") Integer id) {
        return VsResponseUtil.ok(iCateService.getCateById(id));
    }

    @DeleteMapping(UrlConstant.Cate.DELETE_CATE)
    @ApiOperation(value = "Upset active category by id")
    public ResponseEntity<?> upsetActiveCategoryById(@PathVariable("id") Integer id) {
        return VsResponseUtil.ok(iCateService.deleteCateById(id));
    }

    @PostMapping(UrlConstant.Cate.UPLOAD_CATE_DATA_FROM_EXCEL)
    @ApiOperation(value = "Read excel file and save to database")
    public ResponseEntity<?> readFileAndSaveCateData(@RequestParam MultipartFile file) throws IOException, ParseException {
        return VsResponseUtil.ok(fileExcel.saveCateData(uploadFile.convertMultipartToFile(file)));
    }
}
