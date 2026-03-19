package com.filimonov.recipe.website_1.controllers;

import com.filimonov.recipe.website_1.model.Ingredient;
import com.filimonov.recipe.website_1.services.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        Ingredient ingredient = ingredientService.getIngredient(id);
        if (ingredient == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ингредиент не найден");
        }
        return ingredient;
    }

    @GetMapping
    public Collection<Ingredient> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    @PutMapping("/{id}")
    public Ingredient editIngredient(@PathVariable int id, @RequestBody Ingredient ingredient) {
        Ingredient updated = ingredientService.editIngredient(id, ingredient);
        if (updated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ингредиент не найден для редактирования");
        }
        return updated;
    }

    @DeleteMapping("/{id}")
    public Ingredient deleteIngredient(@PathVariable int id) {
        Ingredient deleted = ingredientService.deleteIngredient(id);
        if (deleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ингредиент не найден для удаления");
        }
        return deleted;
    }

}
