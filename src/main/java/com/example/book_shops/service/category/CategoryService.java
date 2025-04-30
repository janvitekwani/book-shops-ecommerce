package com.example.book_shops.service.category;
import com.example.book_shops.Exception.AlreadyExistsException;
import com.example.book_shops.Exception.ResourceNotFoundException;
import com.example.book_shops.model.Category;
import com.example.book_shops.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    /**
     * Retrieves a category by its ID.
     *
     * @param id the ID of the category to retrieve
     * @return the category with the specified ID
     * @throws ResourceNotFoundException if no category is found with the given ID
     */
    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
    }

    /**
     * Retrieves a category by its name.
     *
     * @param name the name of the category to retrieve
     * @return the category with the specified name
     */
    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    /**
     * Retrieves all categories.
     *
     * @return a list of all categories
     */
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Adds a new category to the repository.
     * Ensures that the category name is unique before saving.
     *
     * @param category the category to add
     * @return the added category
     * @throws AlreadyExistsException if a category with the same name already exists
     */
    @Override
    public Category addCategory(Category category) {
        return Optional.of(category)
                .filter(c -> !categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save)
                .orElseThrow(() -> new AlreadyExistsException(category.getName() + " already exists"));
    }

    /**
     * Updates an existing category.
     *
     * @param category the updated category information
     * @param id       the ID of the category to update
     * @return the updated category
     * @throws ResourceNotFoundException if no category is found with the given ID
     */
    @Override
    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(getCategoryById(id))
                .map(oldCategory -> {
                    oldCategory.setName(category.getName());
                    return categoryRepository.save(oldCategory);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
    }

    /**
     * Deletes a category by its ID.
     *
     * @param id the ID of the category to delete
     * @throws ResourceNotFoundException if no category is found with the given ID
     */
    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(
                        categoryRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException("Category not found!");
                        });


        // If the category is found, this part says:
        //"delete that category" using categoryRepository.delete(category).
        //
        //categoryRepository::delete is a method reference.
        //It's a shortcut for writing (category) -> categoryRepository.delete(category).

    }
}
