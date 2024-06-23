package com.foody.service;

import com.foody.model.Category;
import com.foody.model.Food;
import com.foody.model.Restaurant;
import com.foody.repository.FoodRepository;
import com.foody.request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Provider;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImp implements FoodService {
    @Autowired
    private FoodRepository foodRepository;

    /**
     * Creates a new food item and associates it with a category and restaurant.
     *
     * @param req        The request containing food details
     * @param category   The category to which the food belongs
     * @param restaurant The restaurant to which the food belongs
     * @return The created Food object
     */
    @Override
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) {
        Food food = new Food();
        food.setFoodCategory(category);
        food.setRestaurant(restaurant);
        food.setDescription(req.getDescription());
        food.setImages(req.getImages());
        food.setPrice(req.getPrice());
        food.setName(req.getName());
        food.setIngredients(req.getIngredients());
        food.setSeasonal(req.isSeasional());
        food.setVegetarian(req.isVegetarian());

        Food savedFood = foodRepository.save(food);
        restaurant.getFoods().add(savedFood);
        return savedFood; //saves to database
    }

    /**
     * Deletes a food item by its ID.
     *
     * @param foodId The ID of the food item to delete
     * @throws Exception If the food item with the given ID doesn't exist
     */
    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.save(food);
    }

    /**
     * Retrieves a list of food items based on various filters.
     *
     * @param restaurantId  The ID of the restaurant to filter food items
     * @param isVegetarian  Whether to include vegetarian food items
     * @param isNonVeg      Whether to include non-vegetarian food items
     * @param isSeasonal    Whether to include seasonal food items
     * @param foodCategory  The category of food items to filter
     * @return List of filtered food items
     */
    @Override
    public List<Food> getRestaurantFood(Long restaurantId,
                                        boolean isVegetarian,
                                        boolean isNonVeg,
                                        boolean isSeasonal,
                                        String foodCategory) {

        List<Food>  foods = foodRepository.findByRestaurantId(restaurantId);
         if (isVegetarian) {
             foods = filterByVegetarian(foods, isVegetarian);
         }
         if (isNonVeg) {
             foods = filterByNonVeg(foods, isNonVeg);
         }
        if (isSeasonal) {
            foods = filterBySeasonal(foods, isSeasonal);
        }
        if (foodCategory != null && !foodCategory.isEmpty()) {
            foods = filterByCategory(foods, foodCategory);
        }
        return foods;
    }

    /**
     * Filters food items by their category.
     *
     * @param foods       The list of food items to filter
     * @param foodCategory The category name to filter by
     * @return Filtered list of food items
     */
    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food -> {
            if (food.getFoodCategory() != null) {
                return food.getFoodCategory().getName().equals(foodCategory);
            } else {
                return false;
            }
        }).collect(Collectors.toList());
    }

    /**
     * Filters food items by their seasonal availability.
     *
     * @param foods     The list of food items to filter
     * @param isSeasonal Whether to include seasonal food items
     * @return Filtered list of food items
     */
    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeasonal() == isSeasonal).collect(Collectors.toList());
    }

    /**
     * Filters food items by their vegetarian status.
     *
     * @param foods         The list of food items to filter
     * @param isVegetarian  Whether to include vegetarian food items
     * @return Filtered list of food items
     */
    private List<Food> filterByNonVeg(List<Food> foods, boolean isNonVeg) {
        return foods.stream().filter(food -> !food.isVegetarian()).collect(Collectors.toList());
    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {
        return foods.stream().filter(food -> food.isVegetarian() == isVegetarian).collect(Collectors.toList());
    }

    /**
     * Searches for food items based on a keyword.
     *
     * @param keyword The keyword to search for in food names and descriptions
     * @return List of food items matching the search criteria
     */
    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    /**
     * Finds a food item by its ID.
     *
     * @param foodId The ID of the food item to find
     * @return The found Food object
     * @throws Exception If the food item with the given ID doesn't exist
     */
    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> optionalFood = foodRepository.findById(foodId);
        if (optionalFood.isEmpty())
            throw new Exception("Food doesn't exist");
        return optionalFood.get();
    }

    /**
     * Updates the availability status of a food item.
     *
     * @param foodId The ID of the food item to update
     * @return The updated Food object
     * @throws Exception If the food item with the given ID doesn't exist
     */
    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);
    }
}
