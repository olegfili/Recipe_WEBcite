package com.filimonov.recipe.website_1.controllers;

import com.filimonov.recipe.website_1.model.Ingredient;
import com.filimonov.recipe.website_1.services.IngredientService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/ingredients")

public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController (IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    public Ingredient addIngredient (@RequestBody Ingredient newIngredient) {
        return ingredientService.addIngredient(newIngredient);
    }

    @GetMapping("/{id}")
    public Ingredient getIngredient (@PathVariable int id) {
        return ingredientService.getIngredient(id);
    }

    @GetMapping
    public Collection<Ingredient> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    @PutMapping("/{id}")
    public Ingredient editIngredient(@PathVariable int id, @RequestBody Ingredient ingredient) {
        return ingredientService.editIngredient(id, ingredient);
    }

    @DeleteMapping("/{id}")
    public Ingredient deleteIngredient(@PathVariable int id) {
        return ingredientService.deleteIngredient(id);
    }

}
