package project.spring.quanlysach.application.services;

import org.springframework.web.multipart.MultipartFile;
import project.spring.quanlysach.domain.dto.ImageDTO;
import project.spring.quanlysach.domain.entity.Image;

import java.io.IOException;
import java.util.List;

public interface IImageService {

    Image createNewImage(ImageDTO imageDTO, MultipartFile multipartFile);

    List<Image> showAllLinkImage(Integer page, Integer size);

    byte[] getImageById(int id) throws IOException;

    String editImageById(int id, ImageDTO imageDTO, MultipartFile multipartFile);

    String deleteImageById(int id);

    List<Image> showAllImageOfProduct(Integer productId);

    List<Image> showAllImageOfPost(Integer postId);
}
