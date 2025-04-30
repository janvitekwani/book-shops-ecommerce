package com.example.book_shops.repository;

import com.example.book_shops.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     * Finds products by their category name.
     *
     * @param category the category name
     * @return a list of products belonging to the specified category
     */
    List<Product> findByCategoryName(String category);

    /**
     * Finds products by their brand.
     *
     * @param brand the brand name
     * @return a list of products of the specified brand
     */
    List<Product> findByBrand(String brand);

    /**
     * Finds products by both category name and brand.
     *
     * @param category the category name
     * @param brand the brand name
     * @return a list of products matching the specified category and brand
     */
    List<Product> findByCategoryNameAndBrand(String category, String brand);

    /**
     * Finds products by their name.
     *
     * @param name the product name
     * @return a list of products with the specified name
     */
    List<Product> findByName(String name);

    /**
     * Finds products by both brand and name.
     *
     * @param brand the brand name
     * @param name the product name
     * @return a list of products matching the specified brand and name
     */
    List<Product> findByBrandAndName(String brand, String name);

    /**
     * Counts the number of products by both brand and name.
     *
     * @param brand the brand name
     * @param name the product name
     * @return the count of products matching the specified brand and name
     */
    Long countByBrandAndName(String brand, String name);
}