package com.foody.request;

import com.foody.model.Category;
import com.foody.model.IngredientsItem;
import lombok.Data;

import java.util.List;

@Data
public class CreateFoodRequest {
    String name;
    String description;
    Long price;

    private Category category;
    private List<String> images;

    private Long restaurantId;
    private boolean vegetarian;
    private boolean seasional;
    private List<IngredientsItem> ingredients;
}
