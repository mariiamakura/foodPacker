package com.foody.service;

import com.foody.model.IngredientCategory;
import com.foody.model.IngredientsItem;
import com.foody.model.Restaurant;
import com.foody.repository.IngredientCategoryRepository;
import com.foody.repository.IngredientItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImp implements IngredientsService {

    @Autowired
    private IngredientItemRepository ingredietIteamRepository;

    @Autowired
    private IngredientCategoryRepository  ingredientCategoryRepository;

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private IngredientItemRepository ingredientItemRepository;

    /**
     * Creates a new ingredient category associated with a restaurant.
     *
     * @param name         The name of the ingredient category
     * @param restaurantId The ID of the restaurant to which the category belongs
     * @return The created IngredientCategory object
     * @throws Exception If the restaurant with the given ID doesn't exist
     */
    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory ingredientCategory = new IngredientCategory();
        ingredientCategory.setName(name);
        ingredientCategory.setRestaurant(restaurant);
        return ingredientCategoryRepository.save(ingredientCategory);
    }

    /**
     * Finds an ingredient category by its ID.
     *
     * @param id The ID of the ingredient category to find
     * @return The found IngredientCategory object
     * @throws Exception If the ingredient category with the given ID doesn't exist
     */
    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> ingredientCategory = ingredientCategoryRepository.findById(id);
        if (ingredientCategory.isEmpty()) {
            throw new Exception("Ingredient category not found");
        }
        return ingredientCategory.get();
    }

    /**
     * Retrieves all ingredient categories associated with a restaurant.
     *
     * @param restaurantId The ID of the restaurant
     * @return List of IngredientCategory objects belonging to the restaurant
     * @throws Exception If the restaurant with the given ID doesn't exist
     */
    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long restaurantId) throws Exception {
        restaurantService.findRestaurantById(restaurantId);
        return ingredientCategoryRepository.findByRestaurantId(restaurantId);
    }

    /**
     * Creates a new ingredient item associated with a restaurant and a category.
     *
     * @param restaurantId  The ID of the restaurant to which the ingredient item belongs
     * @param ingredientName The name of the ingredient item
     * @param categoryId    The ID of the category to which the ingredient item belongs
     * @return The created IngredientsItem object
     * @throws Exception If the restaurant or ingredient category with the given IDs doesn't exist
     */
    @Override
    public IngredientsItem createIngredientsItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

        IngredientsItem item  = new IngredientsItem();
        item.setName(ingredientName);
        item.setRestaurant(restaurant);

        IngredientCategory ingredientCategory = findIngredientCategoryById(categoryId);
        item.setCategory(ingredientCategory);
        IngredientsItem savedItem = ingredietIteamRepository.save(item);
        ingredientCategory.getIngredients().add(savedItem);
        return savedItem;
    }

    /**
     * Retrieves all ingredient items associated with a restaurant.
     *
     * @param restaurantId The ID of the restaurant
     * @return List of IngredientsItem objects belonging to the restaurant
     */
    @Override
    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) {
        return ingredientItemRepository.findByRestaurantId(restaurantId);
    }

    /**
     * Updates the stock availability status of an ingredient item.
     *
     * @param id The ID of the ingredient item to update
     * @return The updated IngredientsItem object
     * @throws Exception If the ingredient item with the given ID doesn't exist
     */
    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> ingredientsItem = ingredientItemRepository.findById(id);
        if (ingredientsItem.isEmpty()) {
            throw new Exception("Ingredient item not found");
        }
        IngredientsItem item = ingredientsItem.get();
        item.setInStock(!item.isInStock());
        return ingredientItemRepository.save(item);
    }
}
