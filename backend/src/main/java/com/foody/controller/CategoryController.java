package com.foody.controller;

import com.foody.model.Category;
import com.foody.model.User;
import com.foody.service.CategoryService;
import com.foody.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @PostMapping("/admin/category")
    public ResponseEntity<Category> createCategory(@RequestBody Category category,
                                                   @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Category category1 = categoryService.createCategory(category.getName(), user.getId());
        return new ResponseEntity<>(category1, HttpStatus.CREATED);
    }

    @GetMapping("/category/restaurant")
    public ResponseEntity<List<Category>> getRestaurantCategory(@RequestBody Category category,
                                                                @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        List<Category> category1 = categoryService.findCategoryByRestaurantId(user.getId());
        return new ResponseEntity<>(category1, HttpStatus.OK);
    }

}
