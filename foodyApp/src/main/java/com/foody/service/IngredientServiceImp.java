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

    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory ingredientCategory = new IngredientCategory();
        ingredientCategory.setName(name);
        ingredientCategory.setRestaurant(restaurant);
        return ingredientCategoryRepository.save(ingredientCategory);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> ingredientCategory = ingredientCategoryRepository.findById(id);
        if (ingredientCategory.isEmpty()) {
            throw new Exception("Ingredient category not found");
        }
        return ingredientCategory.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long restaurantId) throws Exception {
        restaurantService.findRestaurantById(restaurantId);
        return ingredientCategoryRepository.findByRestaurantId(restaurantId);
    }

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

    @Override
    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) {
        return ingredientItemRepository.findByRestaurantId(restaurantId);
    }

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
