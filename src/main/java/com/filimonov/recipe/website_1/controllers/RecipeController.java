package com.filimonov.recipe.website_1.controllers;

import com.filimonov.recipe.website_1.model.Recipe;
import com.filimonov.recipe.website_1.services.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/recipes")
@Tag(name = "Рецепт", description = "Управление рецептами")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController (RecipeService recipeService){
        this.recipeService = recipeService;
    }

    @PostMapping
    @Operation(summary = "Добавление нового рецепта", description = "Добавляет новый рецепт и сохраняет его")
    public Recipe addRecipe(@RequestBody Recipe newRecipe) {
        if (StringUtils.isBlank(newRecipe.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Название не может быть пустым!");
        }
        return recipeService.addRecipe(newRecipe);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Поиск рецепта по id")
    public Recipe getRecipe(@PathVariable int id) {
        Recipe recipe = recipeService.getRecipe(id);
        if (recipe == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Рецепт не найден");
        }
        return recipe;
    }

    @GetMapping
    @Operation(summary = "Получение списка всех рецептов")
    public Collection<Recipe> getRecipes (@RequestParam(required = false) Integer page){
        if (page != null) {
            return recipeService.getRecipesByPage(page);
        }
        return recipeService.getAllRecipes();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактирование рецепта по id")
    public Recipe editRecipe(@PathVariable int id, @RequestBody Recipe recipe) {
        if (StringUtils.isBlank(recipe.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Название не может быть пустым!");
        }
        return recipeService.editRecipe (id, recipe);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление рецепта")
    public Recipe deleteRecipe(@PathVariable int id) {
        Recipe deleted = recipeService.deleteRecipe(id);
        if (deleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Рецепт не найден для удаления");
        }
        return deleted;
    }

    @GetMapping("/search")
    @Operation(summary = "Поиск рецепта по id ингредиента")
    public Collection<Recipe> searchByIngredient(@RequestParam int ingredientId) {
        return recipeService.searchByIngredient(ingredientId);
    }

    @GetMapping("/searchByIngredients")
    @Operation(summary = "Поиск рецептов по ингредиентам")
    public Collection<Recipe> searchByIngredients(@RequestParam List<String> names) {
        return recipeService.searchByIngredients(names);
    }

}
