package com.example.book_shops.service.product;

import com.example.book_shops.DTO.ProductDto;
import com.example.book_shops.model.Product;
import com.example.book_shops.request.AddProductRequest;
import com.example.book_shops.request.ProductUpdateRequest;

import java.util.List;


public interface IProductService {
    // Adds a new product to the system
    Product addProduct(AddProductRequest product);

    // Retrieves a product by its ID
    Product getProductById(Long id);

    // Deletes a product by its ID
    void deleteProductById(Long id);

    // Updates an existing product's details
    Product updateProduct(ProductUpdateRequest product, Long productId);

    // Retrieves all products in the system
    List<Product> getAllProducts();

    // Retrieves products by their category
    List<Product> getProductsByCategory(String category);

    // Retrieves products by their brand
    List<Product> getProductsByBrand(String brand);

    // Retrieves products by both category and brand
    List<Product> getProductsByCategoryAndBrand(String category, String brand);

    // Retrieves products by their name
    List<Product> getProductsByName(String name);

    // Retrieves products by brand and name
    List<Product> getProductsByBrandAndName(String category, String name);

    // Counts the number of products by brand and name
    Long countProductsByBrandAndName(String brand, String name);

    // Converts a list of products to their DTO representations
    List<ProductDto> getConvertedProducts(List<Product> products);

    // Converts a single product to its DTO representation
    ProductDto convertToDto(Product product);
}