package com.example.book_shops.controller;

import com.example.book_shops.DTO.ProductDto;
import com.example.book_shops.Exception.ResourceNotFoundException;
import com.example.book_shops.model.Product;
import com.example.book_shops.request.AddProductRequest;
import com.example.book_shops.request.ProductUpdateRequest;
import com.example.book_shops.response.ApiResponse;
import com.example.book_shops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final IProductService productService;  // Injecting the product service

    // Endpoint to get all products
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();  // Retrieve all products
        List<ProductDto> convertedProducts = productService.getConvertedProducts(products);  // Convert to DTOs
        return ResponseEntity.ok(new ApiResponse("success", convertedProducts));  // Return success response
    }

    // Endpoint to get a product by its ID
    @GetMapping("product/{productId}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
        try {
            Product product = productService.getProductById(productId);  // Retrieve product by ID
            ProductDto productDto = productService.convertToDto(product);  // Convert to DTO
            return ResponseEntity.ok(new ApiResponse("success", productDto));  // Return success response
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));  // Return error if not found
        }
    }

    // Endpoint to add a new product
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product theProduct = productService.addProduct(product);  // Add product to database
            ProductDto productDto = productService.convertToDto(theProduct);  // Convert to DTO
            return ResponseEntity.ok(new ApiResponse("Add product success!", productDto));  // Return success response
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));  // Return error response if failed
        }
    }

    // Endpoint to update an existing product
    @PutMapping("/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request, @PathVariable Long productId) {
        try {
            Product theProduct = productService.updateProduct(request, productId);  // Update product details
            ProductDto productDto = productService.convertToDto(theProduct);  // Convert to DTO
            return ResponseEntity.ok(new ApiResponse("Update product success!", productDto));  // Return success response
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));  // Return error if not found
        }
    }

    // Endpoint to delete a product by its ID
    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProductById(productId);  // Delete product by ID
            return ResponseEntity.ok(new ApiResponse("Delete product success!", productId));  // Return success response
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));  // Return error if not found
        }
    }

    // Endpoint to get products by brand and name
    @GetMapping("/products/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam String productName) {
        try {
            List<Product> products = productService.getProductsByBrandAndName(brandName, productName);  // Retrieve products by brand and name
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));  // Return error if no products found
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);  // Convert to DTOs
            return ResponseEntity.ok(new ApiResponse("success", convertedProducts));  // Return success response
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));  // Return error response if failed
        }
    }

    // Endpoint to get products by category and brand
    @GetMapping("/products/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category, @RequestParam String brand) {
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);  // Retrieve products by category and brand
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));  // Return error if no products found
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);  // Convert to DTOs
            return ResponseEntity.ok(new ApiResponse("success", convertedProducts));  // Return success response
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", e.getMessage()));  // Return error response if failed
        }
    }

    // Endpoint to get products by name
    @GetMapping("/products/{name}/products")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name) {
        try {
            List<Product> products = productService.getProductsByName(name);  // Retrieve products by name
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));  // Return error if no products found
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);  // Convert to DTOs
            return ResponseEntity.ok(new ApiResponse("success", convertedProducts));  // Return success response
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", e.getMessage()));  // Return error response if failed
        }
    }

    // Endpoint to find products by brand
    @GetMapping("/product/by-brand")
    public ResponseEntity<ApiResponse> findProductByBrand(@RequestParam String brand) {
        try {
            List<Product> products = productService.getProductsByBrand(brand);  // Retrieve products by brand
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));  // Return error if no products found
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);  // Convert to DTOs
            return ResponseEntity.ok(new ApiResponse("success", convertedProducts));  // Return success response
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));  // Return error response if failed
        }
    }

    // Endpoint to get products by category
    @GetMapping("/product/{category}/all/products")
    public ResponseEntity<ApiResponse> findProductByCategory(@PathVariable String category) {
        try {
            List<Product> products = productService.getProductsByCategory(category);  // Retrieve products by category
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));  // Return error if no products found
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);  // Convert to DTOs
            return ResponseEntity.ok(new ApiResponse("success", convertedProducts));  // Return success response
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));  // Return error response if failed
        }
    }

    // Endpoint to count products by brand and name
    @GetMapping("/product/count/by-brand/and-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try {
            var productCount = productService.countProductsByBrandAndName(brand, name);  // Count products by brand and name
            return ResponseEntity.ok(new ApiResponse("Product count!", productCount));  // Return success response with product count
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));  // Return error response if failed
        }
    }

}