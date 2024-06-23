package com.foody.controller;

import com.foody.model.IngredientCategory;
import com.foody.model.IngredientsItem;
import com.foody.request.IngredientCategoryRequest;
import com.foody.request.IngredientRequest;
import com.foody.service.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {
    @Autowired
    private IngredientsService ingredientsService;

    /**
     * Endpoint to create a new ingredient category.
     *
     * @param req the request body containing the ingredient category details
     * @return ResponseEntity containing the created ingredient category and HTTP status
     * @throws Exception if an error occurs during category creation
     */
    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(
            @RequestBody IngredientCategoryRequest req
            ) throws Exception {
        IngredientCategory item = ingredientsService.createIngredientCategory(req.getName(), req.getRestaurantId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);

    }

    /**
     * Endpoint to create a new ingredient item.
     *
     * @param req the request body containing the ingredient item details
     * @return ResponseEntity containing the created ingredient item and HTTP status
     * @throws Exception if an error occurs during ingredient item creation
     */
    @PostMapping()
    public ResponseEntity<IngredientsItem> createIngredientItem(
            @RequestBody IngredientRequest req
            ) throws Exception {
        IngredientsItem item = ingredientsService.createIngredientsItem(req.getRestaurantId(), req.getName(), req.getCategoryId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    /**
     * Endpoint to update the stock of an ingredient item.
     *
     * @param id the ID of the ingredient item to update stock for
     * @return ResponseEntity containing the updated ingredient item and HTTP status
     * @throws Exception if an error occurs during stock update
     */
    @PutMapping("/{id}/stock")
    public ResponseEntity<IngredientsItem> updateIngredientStock(
            @PathVariable Long id
    ) throws Exception {
        IngredientsItem item = ingredientsService.updateStock(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve ingredients of a specific restaurant.
     *
     * @param id the ID of the restaurant to fetch ingredients from
     * @return ResponseEntity containing a list of ingredients and HTTP status
     * @throws Exception if an error occurs while fetching restaurant ingredients
     */
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> getRestaurantIngredients(
            @PathVariable Long id
    ) throws Exception {
        List<IngredientsItem> items = ingredientsService.findRestaurantIngredients(id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve ingredient categories of a specific restaurant.
     *
     * @param id the ID of the restaurant to fetch ingredient categories from
     * @return ResponseEntity containing a list of ingredient categories and HTTP status
     * @throws Exception if an error occurs while fetching restaurant ingredient categories
     */
    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientCategory>> getRestaurantIngredientCategory(
            @PathVariable Long id
    ) throws Exception {
        List<IngredientCategory> items = ingredientsService.findIngredientCategoryByRestaurantId(id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
