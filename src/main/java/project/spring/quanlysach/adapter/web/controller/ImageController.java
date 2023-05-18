package project.spring.quanlysach.adapter.web.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.spring.quanlysach.adapter.web.base.RestApiV0;
import project.spring.quanlysach.application.constants.CommonConstant;
import project.spring.quanlysach.application.constants.UrlConstant;
import project.spring.quanlysach.application.services.IImageService;
import project.spring.quanlysach.domain.dto.ImageDTO;

import java.io.IOException;

@RestApiV0
public class ImageController {
    @Autowired
    private IImageService imageService;

    @PostMapping(UrlConstant.Image.UPLOAD_IMAGE)
    @ApiOperation("Update new image")
    public ResponseEntity<?> uploadNewImage(@ModelAttribute ImageDTO imageDTO,
                                            @RequestParam(value = "file", required = false) MultipartFile multipartFile) {
        return ResponseEntity.ok(imageService.createNewImage(imageDTO, multipartFile));
    }

    @GetMapping(UrlConstant.Image.SHOW_ALL)
    @ApiOperation("Show all image")
    public ResponseEntity<?> getAllImage(@RequestParam(value = "page", required = false) Integer page) {
        return ResponseEntity.ok(imageService.showAllLinkImage(page, CommonConstant.SIZE_OFF_PAGE));
    }

    @GetMapping(UrlConstant.Image.GET_IMAGE_BY_ID)
    public ResponseEntity<?> getImageById(@PathVariable int id) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)//to show content is file image
                    .body(imageService.getImageById(id));
        } catch (IOException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping(UrlConstant.Image.EDIT_IMAGE_BY_ID)
    @ApiOperation("Update image by image id")
    public ResponseEntity<?> updateImage(@RequestBody ImageDTO imageDTO,
                                         @PathVariable(value = "id") int id,
                                         @RequestParam(value = "file", required = false) MultipartFile multipartFile) {
        return ResponseEntity.ok(imageService.editImageById(id, imageDTO, multipartFile));
    }

    @DeleteMapping(UrlConstant.Image.DELETE_IMAGE_BY_ID)
    @ApiOperation("Delete image by image id")
    public ResponseEntity<?> deleteImageByID(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(imageService.deleteImageById(id));
    }

    @GetMapping(UrlConstant.Image.FIND_ALL_IMAGE_OF_POST)
    @ApiOperation("Find all image of a post")
    public ResponseEntity<?> findAllImageOfPost(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(imageService.showAllImageOfPost(id));
    }

    @GetMapping(UrlConstant.Image.FIND_ALL_IMAGE_OF_PRODUCT)
    @ApiOperation("Find all image of a product")
    public ResponseEntity<?> findAllImageOfProduct(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(imageService.showAllImageOfProduct(id));
    }

}
