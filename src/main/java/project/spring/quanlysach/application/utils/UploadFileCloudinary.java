
package project.spring.quanlysach.application.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import project.spring.quanlysach.config.exception.VsException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Component
public class UploadFileCloudinary {
    @Autowired
    private Cloudinary cloudinary;

    public File convertMultipartToFile(MultipartFile file) throws IOException {
        File convertFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream outputStream = new FileOutputStream(convertFile);
        outputStream.write(file.getBytes());
        outputStream.close();
        return convertFile;
    }

    public String getUrlFromFile(MultipartFile multipartFile) {
        try {
            Map<?, ?> mapFile = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.emptyMap());
            //Todo : ObjectUtils.emptyMap : returns an empty 'java.util.Map' object
            //Todo : mapFile.get("secure_url") : is a key used in the metadata returned after uploading an image
            return mapFile.get("secure_url").toString();
        } catch (Exception exception) {
            throw new VsException("The process get url from file failed!");
        }
    }

    public void removeFileToUrl(String url) {
        try {
            cloudinary.uploader().destroy(url, ObjectUtils.emptyMap());
            throw new VsException("Remove file to url is successfully");
        } catch (Exception exception) {
            throw new VsException("Upload image failed");
        }
    }

}
