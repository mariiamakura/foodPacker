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

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodControlller {
    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    /**
     * Endpoint to search for foods by name.
     *
     * @param name the name of the food to search for
     * @param jwt the JWT token from the authorization header
     * @return ResponseEntity containing a list of found foods and HTTP status
     * @throws Exception if an error occurs during food search
     */
    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String name,
                                            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        List<Food> res= foodService.searchFood(name);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve foods of a specific restaurant based on filters.
     *
     * @param vegetarian filter for vegetarian food (true/false)
     * @param seasonal filter for seasonal food (true/false)
     * @param nonVeg filter for non-vegetarian food (true/false)
     * @param restaurantId the ID of the restaurant to fetch foods from
     * @param foodCategory optional category filter for food
     * @param jwt the JWT token from the authorization header
     * @return ResponseEntity containing a list of filtered foods and HTTP status
     * @throws Exception if an error occurs while fetching restaurant foods
     */
    @GetMapping("/reastaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getReastaurantFood(@RequestParam boolean vegetarian,
                                                    @RequestParam boolean seasonal,
                                                    @RequestParam boolean nonVeg,
                                                    @PathVariable Long restaurantId,
                                                 @RequestParam(required = false) String foodCategory,
                                                 @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        List<Food> res= foodService.getRestaurantFood(restaurantId, vegetarian, seasonal, nonVeg, foodCategory);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
