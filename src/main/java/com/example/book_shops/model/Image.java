package com.example.book_shops.model;




import java.sql.Blob;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Unique identifier for the image

    private String fileName;  // Name of the image file

    private String fileType;  // Type/format of the image (e.g., JPEG, PNG)

    @Lob  // Indicates that this field holds large binary data
    private Blob image;  // The image content stored as a large object (LOB)

    private String filePath;  // File path where the image is stored

    private String downloadUrl;  // URL for downloading the image

    @ManyToOne
    @JoinColumn(name="product_id")  // Many images can belong to one product
    private Product product;  // The product associated with the image


}
