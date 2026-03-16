package com.filimonov.recipe.website_1.controllers;

import com.filimonov.recipe.website_1.model.Recipe;
import com.filimonov.recipe.website_1.services.RecipeService;
import org.springframework.web.bind.annotation.*;

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
        return recipeService.getRecipe(id);
    }

    @GetMapping
    public Collection<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @PutMapping("/{id}")
    public Recipe editRecipe(@PathVariable int id, @RequestBody Recipe recipe) {
        return recipeService.editRecipe(id, recipe);
    }

    @DeleteMapping("/{id}")
    public Recipe deleteRecipe(@PathVariable int id) {
        return recipeService.deleteRecipe(id);
    }

    @GetMapping("/search")
    public Collection<Recipe> searchByIngredient(@RequestParam String name) {
        return recipeService.searchByIngredient(name);
    }

    @GetMapping("/searchByIngredients")
    public Collection<Recipe> searchByIngredients(@RequestParam List<String> names) {
        return recipeService.searchByIngredients(names);
    }

    @GetMapping("/page")
    public List<Recipe> getRecipesByPage(@RequestParam(defaultValue = "1") int page) {
        return recipeService.getRecipesByPage(page);
    }
}
