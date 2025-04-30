package com.example.book_shops.service.image;

import com.example.book_shops.DTO.ImageDto;
import com.example.book_shops.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    // Method to retrieve an image by its ID
    Image getImageById(Long id);

    // Method to delete an image by its ID
    void deleteImageById(Long id);

    // Method to save multiple images associated with a product
    List<ImageDto> saveImages(Long productId, List<MultipartFile> files);

    // Method to update an existing image
    void updateImage(MultipartFile file, Long imageId);


}
