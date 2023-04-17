package project.spring.quanlysach.adapter.web.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.spring.quanlysach.adapter.web.base.RestApiV0;
import project.spring.quanlysach.adapter.web.base.VsResponseUtil;
import project.spring.quanlysach.application.constants.CommonConstant;
import project.spring.quanlysach.application.constants.UrlConstant;
import project.spring.quanlysach.application.services.IPostService;
import project.spring.quanlysach.domain.dto.PostDTO;

import javax.validation.Valid;

@RestApiV0
public class PostController {
    @Autowired
    private IPostService iPostService;

    @GetMapping(UrlConstant.Post.DATA_POST)
    @ApiOperation("Show all post")
    public ResponseEntity<?> findAllPost(@RequestParam(name = "page", required = false) Integer page) {
        return VsResponseUtil.ok(iPostService.listPost(page, CommonConstant.SIZE_OFF_PAGE));
    }

    //ok
    @GetMapping(UrlConstant.Post.DATA_POST_LATEST_NEW)
    @ApiOperation("Show all post latest new")
    public ResponseEntity<?> getPostByOrderByDESCreatedTime(@RequestParam(value = "page", required = false) Integer page) {
        return VsResponseUtil.ok(iPostService.showPostByLeastTime(page, CommonConstant.SIZE_OFF_PAGE));
    }

    //ok
    @GetMapping(UrlConstant.Post.FILTER_DATE_BY_CATE)
    @ApiOperation("Filter post by cate name")
    public ResponseEntity<?> filterPostByCateName(@RequestParam(name = "cateName", required = false) String cateName) {
        return VsResponseUtil.ok(iPostService.filterPostByCate(cateName));
    }

    //ok
    @GetMapping(UrlConstant.Post.FILTER_DATE_BY_BRAND)
    @ApiOperation("Filter post by brand id")
    public ResponseEntity<?> getPostByBrandId(@PathVariable("brandId") int brandId) {
        return ResponseEntity.ok(iPostService.filterPostByBrand(brandId));
    }

    //ok
    @PutMapping(UrlConstant.Post.UPDATE_POST)
    @ApiOperation("Update post by post id")
    public ResponseEntity<?> updatePostById(@RequestBody PostDTO postDTO,
                                            @PathVariable("id") int postId) {
        return VsResponseUtil.ok(iPostService.updatePostById(postDTO, postId));
    }

    //ok
    @GetMapping(UrlConstant.Post.GET_POST)
    @ApiOperation("Get post by post id")
    public ResponseEntity<?> getPostById(@PathVariable("id") int id) {
        return VsResponseUtil.ok(iPostService.getPostById(id));
    }

    //ok
    @PostMapping(UrlConstant.Post.CREATE_POST)
    @ApiOperation("Create new post")
    public ResponseEntity<?> createNewPost(@Valid @RequestBody PostDTO postDTO) {
        return VsResponseUtil.ok(iPostService.createNewPost(postDTO));
    }

    @DeleteMapping(UrlConstant.Post.DELETE_POST)
    @ApiOperation("Upset post by post id")
    public ResponseEntity<?> upsetActiveBrandById(@PathVariable("id") Integer id) {
        return VsResponseUtil.ok(iPostService.deletePostById(id));
    }

    @GetMapping(UrlConstant.Post.SHOW_ALL_POST_OF_BRAND_OBOUT_CATE)
    @ApiOperation("Show all post of brand about cate")
    public ResponseEntity<?> showAllPostOfBrandAboutCate(@PathVariable("brandId") int brandId,
                                                         @PathVariable("cateId") int cateId) {
        return VsResponseUtil.ok(iPostService.filterPostOfBrandAboutCateId(brandId, cateId));
    }
}
