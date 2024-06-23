package com.foody.service;

import com.foody.model.Category;
import com.foody.model.Restaurant;
import com.foody.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService{
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Creates a new category for a restaurant.
     *
     * @param name   The name of the category to create
     * @param userId The ID of the user (restaurant owner) creating the category
     * @return The created Category entity
     * @throws Exception if there are errors while creating the category
     */
    @Override
    public Category createCategory(String name, Long userId) throws Exception {
        Restaurant restaurant = restaurantService.getRestaurantByUserId(userId);
        Category category = new Category();
        category.setName(name);
        category.setRestaurant(restaurant);
        return categoryRepository.save(category);
    }

    /**
     * Retrieves all categories belonging to a restaurant.
     *
     * @param id The ID of the restaurant
     * @return A list of categories belonging to the restaurant
     * @throws Exception if there are errors while fetching the categories
     */
    @Override
    public List<Category> findCategoryByRestaurantId(Long id) throws Exception {
        Restaurant restaurant = restaurantService.getRestaurantByUserId(id);
        return categoryRepository.findByRestaurantId(restaurant.getId());
    }

    /**
     * Finds a category by its ID.
     *
     * @param id The ID of the category to find
     * @return The found Category entity
     * @throws Exception if the category with the given ID is not found
     */
    @Override
    public Category findCategoryById(Long id) throws Exception {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new Exception("Category not found");
        }
        return category.get();
    }
}
