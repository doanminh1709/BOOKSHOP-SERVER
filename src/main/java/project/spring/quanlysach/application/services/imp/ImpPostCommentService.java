package project.spring.quanlysach.application.services.imp;

import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.spring.quanlysach.application.constants.DevMessageConstant;
import project.spring.quanlysach.application.constants.UserMessConstant;
import project.spring.quanlysach.application.mapper.PostCommentMapper;
import project.spring.quanlysach.application.repo.CustomerRepository;
import project.spring.quanlysach.application.repo.PostCommentRepository;
import project.spring.quanlysach.application.repo.PostRepository;
import project.spring.quanlysach.application.services.IPostCommentService;
import project.spring.quanlysach.application.utils.UploadFileInProject;
import project.spring.quanlysach.config.exception.VsException;
import project.spring.quanlysach.domain.dto.PostCommentDTO;
import project.spring.quanlysach.domain.entity.Customer;
import project.spring.quanlysach.domain.entity.Post;
import project.spring.quanlysach.domain.entity.PostComment;

import java.util.List;
import java.util.Optional;

@Service
public class ImpPostCommentService implements IPostCommentService {
    private final PostCommentRepository postCommentRepository;
    private final PostRepository postRepository;
    private final CustomerRepository customerRepository;
    private final UploadFileInProject uploadFileInProject;
    // private final UploadFileCloudinary cloudinary;
    private final PostCommentMapper postCommentMapper = Mappers.getMapper(PostCommentMapper.class);

    //, UploadFileCloudinary cloudinary
    public ImpPostCommentService(PostCommentRepository postCommentRepository, PostRepository postRepository, CustomerRepository customerRepository, UploadFileInProject uploadFileInProject) {
        this.postCommentRepository = postCommentRepository;
        this.postRepository = postRepository;
        this.customerRepository = customerRepository;
        this.uploadFileInProject = uploadFileInProject;
    }

    @Override
    public PostComment createNewCommentOfPost(PostCommentDTO postCommentDTO, MultipartFile multipartFile) {
        try {
            Optional<Post> post = postRepository.findById(postCommentDTO.getPostId());
            Optional<Customer> customer = customerRepository.findById(postCommentDTO.getCustomerId());
            if (post.isEmpty() && customer.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.OBJECT_NOT_FOUND));
            } else {
                if (post.isEmpty()) {
                    throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                            String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID,
                                    "post", postCommentDTO.getPostId()));
                } else if (customer.isEmpty()) {
                    throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                            String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID,
                                    "customer", postCommentDTO.getCustomerId()));
                } else {
                    PostComment newComment = postCommentMapper.toPostComment(postCommentDTO,
                            postCommentDTO.getPostId(), postCommentDTO.getCustomerId());
                    if (multipartFile != null) {
                        newComment.setPathImage(uploadFileInProject.converFileToString(multipartFile));
                    }
                    return postCommentRepository.save(newComment);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PostComment> showAllCommentOfPost(Integer page, Integer size) {
        List<PostComment> listComments;
        if (page != null) {
            listComments = postCommentRepository.findAll(PageRequest.of(page.intValue(), size)).getContent();
        } else {
            listComments = postCommentRepository.findAll();
        }
        return listComments;
    }

    @Override
    public List<PostComment> getAllCommentByPostId(int postId) {
        Optional<Post> foundById = postRepository.findById(postId);
        if (foundById.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "post", postId));
        }
        return postCommentRepository.findPostCommentByPostId(postId);
    }

    @Override
    public List<PostComment> getAllCommentByCustomer(int customerId) {
        Optional<Customer> foundCustomerById = customerRepository.findById(customerId);
        if (foundCustomerById.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "customer", customerId));
        }
        return postCommentRepository.findPostCommentByCustomerId(customerId);
    }

    @Override
    public Optional<PostComment> getCommentById(int id) {
        Optional<PostComment> getCommentById = postCommentRepository.findById(id);
        if (getCommentById.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "post comment", id));
        }
        return getCommentById;
    }

    @Override
    public String editCommentById(PostCommentDTO postCommentDTO, int id, MultipartFile multipartFile) {
        Optional<PostComment> foundPostComment = postCommentRepository.findById(id);

        if (foundPostComment.isPresent()) {
            Optional<Post> foundPost = postRepository.findById(postCommentDTO.getPostId());
            Optional<Customer> foundCustomer = customerRepository.findById(postCommentDTO.getCustomerId());
            if (foundCustomer.isEmpty() && foundPost.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.OBJECT_NOT_FOUND));
            } else {
                if (foundPost.isEmpty()) {
                    throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                            String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID,
                                    "post", postCommentDTO.getPostId()));
                } else if (foundCustomer.isEmpty()) {
                    throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                            String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID,
                                    "customer", postCommentDTO.getCustomerId()));
                } else {
                    String getOldPath = foundPostComment.get().getPathImage();
                    foundPostComment = Optional.ofNullable(postCommentMapper.toPostComment(postCommentDTO,
                            postCommentDTO.getPostId(), postCommentDTO.getCustomerId()));

                    if (multipartFile != null) {
                        foundPostComment.get().setPathImage(uploadFileInProject.converFileToString(multipartFile));
                    } else {
                        foundPostComment.get().setPathImage(getOldPath);
                    }
                    foundPostComment.get().setId(id);
                    postCommentRepository.save(foundPostComment.get());
                    return DevMessageConstant.Common.NOTIFICATION_UPDATE_SUCCESS;
                }
            }
        } else {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "post comment", id));
        }
    }

    @Override
    public String removeCommentById(int id) {
        Optional<PostComment> foundPostComment = postCommentRepository.findById(id);
        if (foundPostComment.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "post comment", id));
        }
        postCommentRepository.deleteById(id);
        return DevMessageConstant.Common.NOTIFICATION_DELETE_SUCCESS;
    }

    @Override
    public List<PostComment> showAllCommentLeastNewOfPost(int postId) {
        Optional<Post> foundPostById = postRepository.findById(postId);
        if (foundPostById.isPresent()) {
            List<PostComment> listPostComments = postCommentRepository.findPostCommentByPostIdSoftDESC(postId);
            if (listPostComments.size() > 0) {
                return listPostComments;
            } else {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "post comment", postId));
            }
        } else {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "post", postId));
        }
    }

    @Override
    public List<PostComment> showAllCommentOfCustomerInAPost(int customerId, int postId) {
        Optional<Post> post = postRepository.findById(postId);
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (post.isEmpty() && customer.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.OBJECT_NOT_FOUND));
        } else {
            if (post.isPresent() && customer.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "customer", customerId));
            } else if (post.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "post", postId));
            } else {
                return postCommentRepository.findPostCommentByCustomerIdAndPostId(customerId, postId);
            }
        }
    }
}
