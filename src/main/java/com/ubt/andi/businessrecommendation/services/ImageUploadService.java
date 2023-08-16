package com.ubt.andi.businessrecommendation.services;

import com.ubt.andi.businessrecommendation.models.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageUploadService {
    String uploadImage(MultipartFile imageFile) throws IOException;
    void createImage(Image image);
    void deleteImage(String imageUrl) throws IOException;
}
