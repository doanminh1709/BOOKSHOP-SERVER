package project.spring.quanlysach.application.utils;

import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import project.spring.quanlysach.application.constants.UserMessConstant;
import project.spring.quanlysach.config.exception.VsException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;

@Service
public class UploadFileInProject {
    private final Path storageFolder = Paths.get("uploads");

    public UploadFileInProject() {
        try {
            Files.createDirectories(storageFolder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Upload file to project
    public boolean checkFileValid(MultipartFile file) {
        String fileName = FileNameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[]{"png", "jpg", "jpeg", "bmp"}).contains(fileName.trim().toLowerCase());
    }

    //Read content with link
    public String converFileToString(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT, "File is null");
        }
        if (!checkFileValid(multipartFile)) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT, "File is not valid type");
        }
        //File just less 5 MB
        float fileSizeInMegabytes = multipartFile.getSize() / 1_000_000.0f;
        if (fileSizeInMegabytes > 5.0f) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT, "This capacity of file is not valid");
        }
        //Generate file name
        String filExtension = FileNameUtils.getExtension(multipartFile.getOriginalFilename());
        String randomFileName = UUID.randomUUID().toString().replace("-", "");
        String fileName = randomFileName + "." + filExtension;
        //create new file in path folder
        Path destinationFileName = this.storageFolder.resolve(Paths.get(fileName)).normalize().toAbsolutePath();
        if (!destinationFileName.getParent().equals(this.storageFolder.toAbsolutePath())) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT, "Cannot store file outside ");
        }
        //Copy file to folder
        try {
            InputStream inputStream = multipartFile.getInputStream();
            Files.copy(inputStream, destinationFileName, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] loadResource(String fileName) throws Exception {
        try {
            //resolve path from relative path to absolute path
            Path file = storageFolder.resolve(fileName);
            //Load resources from class path
            Resource resource = new UrlResource(file.toUri());
            //isReadable : check sources represented 'Resource' object
            if (resource.exists() || resource.isReadable()) {
                return StreamUtils.copyToByteArray(resource.getInputStream());
            }
        } catch (IOException exception) {
            throw new Exception("Could not load file", exception);
        }
        return null;
    }


    private boolean isImageFile(MultipartFile file) {
        //Let install FileNameUtils
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[]{"png", "jpg", "jpeg", "bmp"})
                .contains(fileExtension.trim().toLowerCase());
    }

    //Get image by id

    public String storeFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file.");
            }
            //check file is image ?
            if (!isImageFile(file)) {
                throw new RuntimeException("You can only upload image file");
            }
            //file must be <= 5Mb
            float fileSizeInMegabytes = file.getSize() / 1_000_000.0f;
            if (fileSizeInMegabytes > 5.0f) {
                throw new RuntimeException("File must be <= 5Mb");
            }
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generatedFileName = UUID.randomUUID().toString().replace("-", "");
            generatedFileName = generatedFileName + "." + fileExtension;
            //Constructive a path repersent
            Path destinationFilePath = this.storageFolder.
                    resolve(Paths.get(generatedFileName))//create new path
                    .normalize().toAbsolutePath();
            if (!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())) {
                throw new RuntimeException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            return generatedFileName;
        } catch (IOException exception) {
            throw new RuntimeException("Failed to store file.", exception);
        }
    }

    //Load image
    public byte[] loadImage(String name) throws IOException {
        try {
            Path path = storageFolder.resolve(name);
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                //Copy content of InputStream to Byte array
                return StreamUtils.copyToByteArray(resource.getInputStream());
            } else {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT, "Can not read file");
            }
        } catch (Exception e) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT, e.getMessage());
        }
    }

    //Delete image by id
    public String deleteImage(String file_name) {

        Path path = storageFolder.toAbsolutePath();//absolute file
        Path resolve_path = storageFolder.resolve(path);
        try {
            Files.deleteIfExists(resolve_path);
            return "Delete file success";
        } catch (Exception e) {
            return "Delete file failed";
        }
    }
}
