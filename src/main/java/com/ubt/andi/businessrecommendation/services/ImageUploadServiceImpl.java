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
    public void uploadImage(MultipartFile imageFile) throws IOException {
        String filepath = "src/main/resources/images/" + imageFile.getOriginalFilename();
        Path destination = Paths.get(filepath);
        Files.copy(imageFile.getInputStream(),destination, StandardCopyOption.REPLACE_EXISTING);
    }
}
