package com.foody.controller;

import com.foody.model.Food;
import com.foody.model.Restaurant;
import com.foody.model.User;
import com.foody.request.CreateFoodRequest;
import com.foody.response.MessageResponse;
import com.foody.service.FoodService;
import com.foody.service.RestaurantService;
import com.foody.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/food")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    /**
     * Endpoint to create a new food item.
     *
     * @param req the request body containing food details
     * @param jwt the JWT token from the authorization header
     * @return ResponseEntity containing the created food item and HTTP status
     * @throws Exception if an error occurs during food creation
     */
    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req,
                                           @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(req.getRestaurantId());
        Food food = foodService.createFood(req, req.getCategory(), restaurant);
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    /**
     * Endpoint to delete a food item by ID.
     *
     * @param id the ID of the food item to delete
     * @param jwt the JWT token from the authorization header
     * @return ResponseEntity containing a message and HTTP status
     * @throws Exception if an error occurs during food deletion
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id,
                                                      @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        foodService.deleteFood(id);
        MessageResponse msg = new MessageResponse();
        msg.setMessage("Food deleted successfully");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    /**
     * Endpoint to update the availability status of a food item by ID.
     *
     * @param id the ID of the food item to update
     * @param jwt the JWT token from the authorization header
     * @return ResponseEntity containing the updated food item and HTTP status
     * @throws Exception if an error occurs during food status update
     */
    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvailabilityStatus(@PathVariable Long id,
                                                      @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.updateAvailabilityStatus(id);

        return new ResponseEntity<>(food, HttpStatus.OK);
    }

}
