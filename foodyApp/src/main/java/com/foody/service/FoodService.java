package com.foody.service;

import com.foody.model.Category;
import com.foody.model.Food;
import com.foody.model.Restaurant;
import com.foody.request.CreateFoodRequest;
import lombok.Lombok;

import java.util.List;

public interface FoodService {
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);
    void deleteFood(Long foodId) throws Exception;
    public List<Food> getRestaurantFood(Long restaurantId, boolean isVegetarian,
                                        boolean isNonVeg, boolean isSeasonal,
                                        String foodCategory);
    public List<Food> searchFood(String keyword);
    public Food findFoodById(Long foodId) throws Exception;
    public Food updateAvailabilityStatus(Long foodId) throws Exception;

}
