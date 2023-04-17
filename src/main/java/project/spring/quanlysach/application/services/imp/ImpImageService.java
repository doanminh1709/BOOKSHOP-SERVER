package project.spring.quanlysach.application.services.imp;

import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.spring.quanlysach.application.constants.DevMessageConstant;
import project.spring.quanlysach.application.constants.UserMessConstant;
import project.spring.quanlysach.application.mapper.ImageMapper;
import project.spring.quanlysach.application.repo.ImageRepository;
import project.spring.quanlysach.application.repo.PostRepository;
import project.spring.quanlysach.application.repo.ProductRepository;
import project.spring.quanlysach.application.services.IImageService;
import project.spring.quanlysach.application.utils.UploadFileInProject;
import project.spring.quanlysach.config.exception.VsException;
import project.spring.quanlysach.domain.dto.ImageDTO;
import project.spring.quanlysach.domain.entity.Image;
import project.spring.quanlysach.domain.entity.Post;
import project.spring.quanlysach.domain.entity.Product;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ImpImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final PostRepository postRepository;
    private final ImageMapper imageMapper = Mappers.getMapper(ImageMapper.class);
    private final UploadFileInProject uploadFileInProject;

    public ImpImageService(ImageRepository imageRepository, ProductRepository productRepository,
                           PostRepository postRepository, UploadFileInProject uploadFileInProject) {
        this.imageRepository = imageRepository;
        this.productRepository = productRepository;
        this.postRepository = postRepository;
        this.uploadFileInProject = uploadFileInProject;
    }

    @Override
    public Image createNewImage(ImageDTO imageDTO, MultipartFile multipartFile) {
        Image newImage = imageMapper.toImage(imageDTO);
        if (multipartFile != null) {
            newImage.setPath(uploadFileInProject.converFileToString(multipartFile));
        }
        if (imageDTO.getPostId() != null) {
            newImage.setPost(new Post(imageDTO.getPostId()));
        }
        if (imageDTO.getProductId() != null) {
            newImage.setProduct(new Product(imageDTO.getProductId()));
        }
        return imageRepository.save(newImage);
    }

    @Override
    public List<Image> showAllLinkImage(Integer page, Integer size) {
        List<Image> listImage;
        if (page != null) {
            listImage = imageRepository.findAll(PageRequest.of(page.intValue(), size)).getContent();
        } else {
            listImage = imageRepository.findAll();
        }
        return listImage;
    }

    @Override
    public byte[] getImageById(int id) throws IOException {
        Optional<Image> foundImage = imageRepository.findById(id);
        if (foundImage.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "Image", id));
        } else {
            return uploadFileInProject.loadImage(foundImage.get().getPath());
        }
    }

    @Override
    public String editImageById(int id, ImageDTO imageDTO, MultipartFile multipartFile) {
        Optional<Image> foundImage = imageRepository.findById(id);
        if (foundImage.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "Image", id));
        }
        String getOldPath = foundImage.get().getPath();
        foundImage = Optional.ofNullable(imageMapper.toImage(imageDTO));
        if (multipartFile != null) {
            String newPath = uploadFileInProject.converFileToString(multipartFile);
            foundImage.get().setPath(newPath);
        } else {
            foundImage.get().setPath(getOldPath);
        }
        foundImage.get().setId(id);
        imageRepository.save(foundImage.get());
        return DevMessageConstant.Common.NOTIFICATION_UPDATE_SUCCESS;
    }

    @Override
    public String deleteImageById(int id) {
        Optional<Image> foundImage = imageRepository.findById(id);
        if (foundImage.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "Image", id));
        }
        try {
            uploadFileInProject.deleteImage(foundImage.get().getPath());
            imageRepository.deleteById(id);
            return DevMessageConstant.Common.NOTIFICATION_DELETE_SUCCESS;
        } catch (Exception e) {
            return DevMessageConstant.Common.NOTIFICATION_DELETE_FAILED;
        }
    }

    @Override
    public List<Image> showAllImageOfProduct(Integer productId) {
        if (productId == null) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.ID_EMPTY));
        }
        Optional<Product> foundByProduct = productRepository.findById(productId);
        if (foundByProduct.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "product", productId));
        }
        return imageRepository.findImageByProductId(productId);
    }

    @Override
    public List<Image> showAllImageOfPost(Integer postId) {
        if (postId == null) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.ID_EMPTY));
        }
        Optional<Post> foundByPost = postRepository.findById(postId);
        if (foundByPost.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "post", postId));
        }
        return imageRepository.findImageByPostId(postId);
    }
}
