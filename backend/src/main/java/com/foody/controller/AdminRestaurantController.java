package com.foody.controller;

import com.foody.model.Restaurant;
import com.foody.model.User;
import com.foody.request.CreateRestaurantRequest;
import com.foody.response.MessageResponse;
import com.foody.service.RestaurantService;
import com.foody.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;

    /**
     * Endpoint to create a new restaurant.
     *
     * @param req the request body containing restaurant details
     * @param jwt the JWT token from the authorization header
     * @return ResponseEntity containing the created restaurant and HTTP status
     * @throws Exception if an error occurs during restaurant creation
     */
    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.createRestaurant(req, user);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    /**
     * Endpoint to update an existing restaurant.
     *
     * @param req the request body containing updated restaurant details
     * @param jwt the JWT token from the authorization header
     * @param id the ID of the restaurant to update
     * @return ResponseEntity containing the updated restaurant and HTTP status
     * @throws Exception if an error occurs during restaurant update
     */
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.updateRestaurant(id, req);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    /**
     * Endpoint to delete a restaurant by ID.
     *
     * @param jwt the JWT token from the authorization header
     * @param id the ID of the restaurant to delete
     * @return ResponseEntity containing a message and HTTP status
     * @throws Exception if an error occurs during restaurant deletion
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        restaurantService.deleteRestaurant(id);
        MessageResponse res = new MessageResponse();
        res.setMessage("Restaurant deleted successfully.");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * Endpoint to update the status of a restaurant by ID.
     *
     * @param jwt the JWT token from the authorization header
     * @param id the ID of the restaurant to update status
     * @return ResponseEntity containing the updated restaurant and HTTP status
     * @throws Exception if an error occurs during restaurant status update
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Restaurant restaurant = restaurantService.updateRestaurantStatus(id);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    /**
     * Endpoint to find a restaurant by the currently authenticated user's ID.
     *
     * @param jwt the JWT token from the authorization header
     * @return ResponseEntity containing the found restaurant and HTTP status
     * @throws Exception if an error occurs while fetching the restaurant
     */
    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantByUserId(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

}
