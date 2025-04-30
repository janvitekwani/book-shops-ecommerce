package com.example.book_shops.service.image;

import com.example.book_shops.DTO.ImageDto;
import com.example.book_shops.Exception.ResourceNotFoundException;
import com.example.book_shops.model.Image;
import com.example.book_shops.model.Product;
import com.example.book_shops.repository.ImageRepository;
import com.example.book_shops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




// ImageService implementation handling image-related operations
@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    // Dependencies injected using constructor injection
    private final ImageRepository imageRepository;
    private final IProductService productService;

    @Override
    public Image getImageById(Long id) {
        // Fetch image by ID, throws exception if not found
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No image found with id: " + id));
    }

    @Override
    public void deleteImageById(Long id) {
        // Check if image exists, if yes delete, otherwise throw exception
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> {
            throw new ResourceNotFoundException("No image found with id: " + id);
        });
    }

    @Override
    public List<ImageDto> saveImages(Long productId, List<MultipartFile> files) {
        // Fetch the product associated with the images
        Product product = productService.getProductById(productId);
        List<ImageDto> savedImageDto = new ArrayList<>();

        // Loop through all files and save each image
        for (MultipartFile file : files) {
            try {
                // Create new Image object and set its properties
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));  // Convert file content to Blob for storage
                image.setProduct(product);  // Associate image with the product

                // Generate download URL for the image
                String buildDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl = buildDownloadUrl + image.getId();
                image.setDownloadUrl(downloadUrl);

                // Save the image to the repository and retrieve it again to ensure download URL is set
                Image savedImage = imageRepository.save(image);
                savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
                imageRepository.save(savedImage);

                // Create and populate ImageDto to return
                ImageDto imageDto = new ImageDto();
                imageDto.setId(savedImage.getId());
                imageDto.setFileName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());

                // Add ImageDto to the list to be returned
                savedImageDto.add(imageDto);
            } catch (IOException | SQLException e) {
                // Handle exceptions during file processing and image saving
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        // Fetch the existing image by its ID
        Image image = getImageById(imageId);
        try {
            // Update the image's properties with the new file data
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));  // Update image content
            imageRepository.save(image);  // Save the updated image to the repository
        } catch (IOException | SQLException e) {
            // Handle exceptions during file processing and image update
            throw new RuntimeException(e.getMessage());
        }
    }
}