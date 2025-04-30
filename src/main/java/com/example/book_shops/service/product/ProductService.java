    package com.example.book_shops.service.product;

    import com.example.book_shops.DTO.ImageDto;
    import com.example.book_shops.DTO.ProductDto;
    import com.example.book_shops.Exception.ResourceNotFoundException;
    import com.example.book_shops.model.Category;
    import com.example.book_shops.model.Image;
    import com.example.book_shops.model.Product;
    import com.example.book_shops.repository.CategoryRepository;
    import com.example.book_shops.repository.ImageRepository;
    import com.example.book_shops.repository.ProductRepository;
    import com.example.book_shops.request.AddProductRequest;
    import com.example.book_shops.request.ProductUpdateRequest;
    import lombok.RequiredArgsConstructor;
    import org.modelmapper.ModelMapper;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.Optional;


    @Service
    @RequiredArgsConstructor
    public class ProductService implements IProductService {
        // Inject dependencies
        private final ProductRepository productRepository;
        private final CategoryRepository categoryRepository;
        private final ModelMapper modelMapper;
        private final ImageRepository imageRepository;

        // Adds a new product to the system
        @Override
        public Product addProduct(AddProductRequest request) {
            // Check if the category exists, otherwise create and save it
            Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                    .orElseGet(() -> {
                        Category newCategory = new Category(request.getCategory().getName());
                        return categoryRepository.save(newCategory);
                    });
            // Set the category for the product and save it
            request.setCategory(category);
            return productRepository.save(createProduct(request, category));
        }

        // Creates a Product object from the AddProductRequest
        private Product createProduct(AddProductRequest request, Category category) {
            return new Product(
                    request.getName(),
                    request.getBrand(),
                    request.getDescription(),
                    request.getPrice(),
                    request.getInventory(),
                    category
            );
        }

        // Fetches a product by its ID, throws an exception if not found
        @Override
        public Product getProductById(Long id) {
            return productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
        }

        // Deletes a product by its ID, throws an exception if not found
        @Override
        public void deleteProductById(Long id) {
            productRepository.findById(id)
                    .ifPresentOrElse(productRepository::delete,
                            () -> {
                                throw new ResourceNotFoundException("Product not found!");
                            });
        }

        // Updates an existing product
        @Override
        public Product updateProduct(ProductUpdateRequest request, Long productId) {
            return productRepository.findById(productId)
                    .map(existingProduct -> updateExistingProduct(existingProduct, request))
                    .map(productRepository::save)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
        }

        // Helper method to update the fields of an existing product
        private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
            existingProduct.setName(request.getName());
            existingProduct.setBrand(request.getBrand());
            existingProduct.setPrice(request.getPrice());
            existingProduct.setInventory(request.getInventory());
            existingProduct.setDescription(request.getDescription());

            // Set the category from the request
            Category category = categoryRepository.findByName(request.getCategory().getName());
            existingProduct.setCategory(category);
            return existingProduct;
        }

        // Fetches all products from the database
        @Override
        public List<Product> getAllProducts() {
            return productRepository.findAll();
        }

        // Fetches products based on the category name
        @Override
        public List<Product> getProductsByCategory(String category) {
            return productRepository.findByCategoryName(category);
        }

        // Fetches products based on the brand name
        @Override
        public List<Product> getProductsByBrand(String brand) {
            return productRepository.findByBrand(brand);
        }

        // Fetches products based on both category and brand
        @Override
        public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
            return productRepository.findByCategoryNameAndBrand(category, brand);
        }

        // Fetches products based on the product name
        @Override
        public List<Product> getProductsByName(String name) {
            return productRepository.findByName(name);
        }

        // Fetches products based on both brand and name
        @Override
        public List<Product> getProductsByBrandAndName(String brand, String name) {
            return productRepository.findByBrandAndName(brand, name);
        }

        // Counts products based on brand and name
        @Override
        public Long countProductsByBrandAndName(String brand, String name) {
            return productRepository.countByBrandAndName(brand, name);
        }

        // Converts a list of products to DTOs
        @Override
        public List<ProductDto> getConvertedProducts(List<Product> products) {
            return products.stream().map(this::convertToDto).toList();
        }

        // Converts a product to its corresponding DTO
        @Override
        public ProductDto convertToDto(Product product) {
            ProductDto productDto = modelMapper.map(product, ProductDto.class);

            // Convert images related to the product to ImageDto
            List<Image> images = imageRepository.findByProductId(product.getId());
            List<ImageDto> imageDtos = images.stream()
                    .map(image -> modelMapper.map(image, ImageDto.class))
                    .toList();

            // Set the images in the product DTO
            productDto.setImages(imageDtos);
            return productDto;
        }
    }