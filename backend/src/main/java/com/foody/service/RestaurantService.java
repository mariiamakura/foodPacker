package com.foody.service;

import com.foody.dto.RestaurantDto;
import com.foody.model.Restaurant;
import com.foody.model.User;
import com.foody.request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user);

    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRestaurant) throws Exception;

    public void deleteRestaurant(Long restaurantId) throws Exception;
    public List<Restaurant> getAllRestaurants();
    public List<Restaurant> searchRestaurants(String keyword);
    public Restaurant findRestaurantById(Long restaurantId) throws Exception;
    public Restaurant getRestaurantByUserId(Long userId) throws Exception;
    public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception;
    public Restaurant updateRestaurantStatus(Long id) throws Exception;
}
