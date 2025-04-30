package com.example.book_shops.service.category;

import com.example.book_shops.model.Category;

import java.util.List;

public interface ICategoryService {
    /**
     * Retrieves a category by its ID.
     *
     * @param id the ID of the category to retrieve
     * @return the category with the specified ID
     */
    Category getCategoryById(Long id);

    /**
     * Retrieves a category by its name.
     *
     * @param name the name of the category to retrieve
     * @return the category with the specified name
     */
    Category getCategoryByName(String name);

    /**
     * Retrieves all categories.
     *
     * @return a list of all categories
     */
    List<Category> getAllCategories();

    /**
     * Adds a new category to the repository.
     * Ensures that the category name is unique before saving.
     *
     * @param category the category to add
     * @return the added category
     */
    Category addCategory(Category category);

    /**
     * Updates an existing category.
     *
     * @param category the updated category information
     * @param id       the ID of the category to update
     * @return the updated category
     */
    Category updateCategory(Category category, Long id);

    /**
     * Deletes a category by its ID.
     *
     * @param id the ID of the category to delete
     */
    void deleteCategoryById(Long id);

}
