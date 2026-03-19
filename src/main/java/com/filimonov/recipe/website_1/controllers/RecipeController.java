package com.filimonov.recipe.website_1.controllers;

import com.filimonov.recipe.website_1.model.Recipe;
import com.filimonov.recipe.website_1.services.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/recipes")

public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController (RecipeService recipeService){
        this.recipeService = recipeService;
    }

    @PostMapping
    public Recipe addRecipe(@RequestBody Recipe newRecipe) {
        return recipeService.addRecipe(newRecipe);
    }

    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable int id) {
        Recipe recipe = recipeService.getRecipe(id);
        if (recipe == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Рецепт не найден");
        }
        return recipe;
    }

    @GetMapping
    public Collection<Recipe> getRecipes (@RequestParam(required = false) Integer page){
        if (page != null) {
            return recipeService.getRecipesByPage(page);
        }
        return recipeService.getAllRecipes();
    }

    @PutMapping("/{id}")
    public Recipe editRecipe ( @PathVariable int id, @RequestBody Recipe recipe) {
        Recipe updated = recipeService.editRecipe(id, recipe);
        if (updated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Рецепт не найден для редактирования");
        }
        return updated;
    }


    @DeleteMapping("/{id}")
    public Recipe deleteRecipe(@PathVariable int id) {
        Recipe deleted = recipeService.deleteRecipe(id);
        if (deleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Рецепт не найден для удаления");
        }
        return deleted;
    }

    @GetMapping("/search")
    public Collection<Recipe> searchByIngredient(@RequestParam int ingredientId) {
        return recipeService.searchByIngredient(ingredientId);
    }

    @GetMapping("/searchByIngredients")
    public Collection<Recipe> searchByIngredients(@RequestParam List<String> names) {
        return recipeService.searchByIngredients(names);
    }

}
