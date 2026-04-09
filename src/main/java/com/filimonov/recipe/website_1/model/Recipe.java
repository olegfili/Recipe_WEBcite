package com.filimonov.recipe.website_1.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Recipe {
    private int id;
    private String name;
    private int timeToCook;
    private List<Ingredient> ingredients = new ArrayList<>();


}
