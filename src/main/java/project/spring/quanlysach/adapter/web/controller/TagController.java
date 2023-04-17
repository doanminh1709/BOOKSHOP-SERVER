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
import project.spring.quanlysach.application.services.ITagService;
import project.spring.quanlysach.application.utils.UploadFileCloudinary;
import project.spring.quanlysach.domain.dto.TagDTO;

import java.io.IOException;

@RestApiV0
public class TagController {
    @Autowired
    private ITagService iTagService;
    @Autowired
    private ReadFileExcel fileExcel;

    @Autowired
    private UploadFileCloudinary uploadFile;

    @GetMapping(UrlConstant.Tag.DATA_TAG)
    @ApiOperation("Show all information of tag")
    public ResponseEntity<?> getFindAllTag(@RequestParam(name = "page", required = false) Integer page) {
        return VsResponseUtil.ok(iTagService.ListTag(page, CommonConstant.SIZE_OFF_PAGE));
    }

    @PostMapping(UrlConstant.Tag.CREATE_TAG)
    @ApiOperation("Create new tag")
    public ResponseEntity<?> createNewTag(@RequestBody TagDTO tagDTO) {
        return VsResponseUtil.ok(iTagService.createNewTag(tagDTO));
    }

    @PutMapping(UrlConstant.Tag.UPDATE_TAG)
    @ApiOperation("Update information of tag by tag id")
    public ResponseEntity<?> updateOldTag(@PathVariable("id") Integer id, @RequestBody TagDTO tagDTO) {
        return VsResponseUtil.ok(iTagService.updateTagById(tagDTO, id));
    }

    @GetMapping(UrlConstant.Tag.GET_TAG)
    @ApiOperation("Get information of tag by tag id")
    public ResponseEntity<?> getTagById(@PathVariable("id") Integer id) {
        return VsResponseUtil.ok(iTagService.getTagById(id));
    }

    @DeleteMapping(UrlConstant.Tag.DELETE_TAG)
    @ApiOperation("Delete information of tag by tag id")
    public ResponseEntity<?> deleteTagById(@PathVariable("id") Integer id) {
        return VsResponseUtil.ok(iTagService.deleteTagById(id));
    }

    @PostMapping(UrlConstant.Tag.UPLOAD_TAG_DATA_FROM_EXCEL)
    @ApiOperation("Upload excel file to tag data")
    public ResponseEntity<?> uploadTagDataFromExcel(@RequestParam MultipartFile multipartFile) throws IOException {
        return VsResponseUtil.ok(fileExcel.saveTagData((uploadFile.convertMultipartToFile(multipartFile))));
    }
}
