package com.ubt.andi.businessrecommendation.services;

import com.ubt.andi.businessrecommendation.models.Image;
import com.ubt.andi.businessrecommendation.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImageUploadServiceImpl implements ImageUploadService{
    private ImageRepository imageRepository;
    @Autowired
    public ImageUploadServiceImpl(ImageRepository imageRepository){
        this.imageRepository=imageRepository;
    }
    @Override
    public void createImage(Image image) {
        if(image == null) return;
        imageRepository.save(image);
    }
    @Override
    public String uploadImage(MultipartFile imageFile) throws IOException {
        String baseFile = "src/main/resources/static/images/";
        String fileName = imageFile.getOriginalFilename();
        String filePath = baseFile + fileName;
        Path destination = Paths.get(filePath);
        int counter = 1;
        while(Files.exists(destination)){
            fileName = imageFile.getOriginalFilename().replace(".","_" + counter + ".");
            destination = Paths.get(baseFile + fileName);
            counter++;
        }
        Files.copy(imageFile.getInputStream(),destination, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }
    @Override
    public void deleteImage(String imageUrl) throws IOException {
        String baseFile = "src/main/resources/static/images/";
        String filePath = baseFile + imageUrl;
        Path destination = Paths.get(filePath);
        Files.deleteIfExists(destination);
        imageRepository.deleteImageByUrl(imageUrl);
    }
}
