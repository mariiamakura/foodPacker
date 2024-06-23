package com.foody.controller;

import com.foody.dto.RestaurantDto;
import com.foody.model.Restaurant;
import com.foody.model.User;
import com.foody.request.CreateRestaurantRequest;
import com.foody.service.RestaurantService;
import com.foody.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;

    /**
     * Endpoint to search for restaurants by keyword.
     *
     * @param jwt the JWT token from the authorization header
     * @param keyword the keyword to search for in restaurant names
     * @return ResponseEntity containing a list of found restaurants and HTTP status
     * @throws Exception if an error occurs during restaurant search
     */
    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String keyword
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Restaurant> restaurant = restaurantService.searchRestaurants(keyword);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve all restaurants.
     *
     * @param jwt the JWT token from the authorization header
     * @return ResponseEntity containing a list of all restaurants and HTTP status
     * @throws Exception if an error occurs while fetching restaurants
     */
    @GetMapping()
    public ResponseEntity<List<Restaurant>> getAllRestaurant(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Restaurant> restaurant = restaurantService.getAllRestaurants();
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a restaurant by ID.
     *
     * @param jwt the JWT token from the authorization header
     * @param id the ID of the restaurant to fetch
     * @return ResponseEntity containing the fetched restaurant and HTTP status
     * @throws Exception if an error occurs while fetching the restaurant
     */
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    /**
     * Endpoint to add a restaurant to user's favorites.
     *
     * @param jwt the JWT token from the authorization header
     * @param id the ID of the restaurant to add to favorites
     * @return ResponseEntity containing the updated restaurant DTO and HTTP status
     * @throws Exception if an error occurs while adding to favorites
     */
    @PutMapping("/{id}/add-favorites")
    public ResponseEntity<RestaurantDto> addToFavorites(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        RestaurantDto restaurant = restaurantService.addToFavorites(id, user);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

}
