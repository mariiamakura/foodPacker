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

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String name,
                                            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        List<Food> res= foodService.searchFood(name);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

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
