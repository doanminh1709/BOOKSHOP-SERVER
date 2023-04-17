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
import project.spring.quanlysach.application.services.IPostCommentService;
import project.spring.quanlysach.domain.dto.PostCommentDTO;

@RestApiV0
public class PostCommentController {

    @Autowired
    private IPostCommentService postCommentService;

    @PostMapping(UrlConstant.PostComment.CREATE_POST_COMMENT)
    @ApiOperation("Create new comment of post")
    public ResponseEntity<?> createNewCommentOfPost(@ModelAttribute PostCommentDTO postCommentDTO,
                                                    @RequestParam(value = "file", required = false) MultipartFile file) {
        return VsResponseUtil.ok(postCommentService.createNewCommentOfPost(postCommentDTO, file));
    }

    @GetMapping(UrlConstant.PostComment.GET_ALL_COMMENT)
    @ApiOperation("See all comment of a post")
    public ResponseEntity<?> showAllCommentOfPost(@RequestParam(value = "page", required = false) Integer page) {
        return VsResponseUtil.ok(postCommentService.showAllCommentOfPost(page, CommonConstant.SIZE_OFF_PAGE));
    }

    @GetMapping(UrlConstant.PostComment.GET_COMMENT_OF_POST)
    @ApiOperation("Get all comment of post")
    public ResponseEntity<?> getAllCommentsByPost(@PathVariable(value = "postId") Integer postId) {
        return VsResponseUtil.ok(postCommentService.getAllCommentByPostId(postId));
    }

    @GetMapping(UrlConstant.PostComment.GET_COMMENT_OF_CUSTOMER)
    @ApiOperation("Get all comment by customer")
    public ResponseEntity<?> getAllCommentByCustomer(@PathVariable(value = "customerId") Integer customerId) {
        return VsResponseUtil.ok(postCommentService.getAllCommentByCustomer(customerId));
    }

    @GetMapping(UrlConstant.PostComment.GET_COMMENT_BY_ID)
    @ApiOperation("Get comment by comment id")
    public ResponseEntity<?> getCommentById(@PathVariable(value = "id") Integer id) {
        return VsResponseUtil.ok(postCommentService.getCommentById(id));
    }

    @PatchMapping(UrlConstant.PostComment.EDIT_COMMENT)
    @ApiOperation("Edit comment by comment id")
    public ResponseEntity<?> editComment(@ModelAttribute PostCommentDTO postCommentDTO,
                                         @PathVariable(value = "id") Integer id,
                                         @RequestParam(value = "file", required = false) MultipartFile file) {
        return VsResponseUtil.ok(postCommentService.editCommentById(postCommentDTO, id, file));
    }

    @DeleteMapping(UrlConstant.PostComment.REMOVE_COMMENT)
    @ApiOperation("Remove comment by comment id")
    public ResponseEntity<?> removeComment(@PathVariable(value = "id") Integer id) {
        return VsResponseUtil.ok(postCommentService.removeCommentById(id));
    }

    @GetMapping(UrlConstant.PostComment.GET_LEAST_NEW_COMMENT_OF_POST)
    @ApiOperation("Show list new comment of post")
    public ResponseEntity<?> showAllCommentLeastNew(@PathVariable("postId") int postId) {
        return VsResponseUtil.ok(postCommentService.showAllCommentLeastNewOfPost(postId));
    }


    @GetMapping(UrlConstant.PostComment.SHOW_ALL_COMMENT_OF_CUSTOMER_IN_POST)
    @ApiOperation("Show all comment of customer in a post")
    public ResponseEntity<?> showAllCommentOfCustomerInAPost(@PathVariable("customerId") int customerId,
                                                             @PathVariable("postId") int postId) {
        return VsResponseUtil.ok(postCommentService.showAllCommentOfCustomerInAPost(customerId, postId));
    }

}
