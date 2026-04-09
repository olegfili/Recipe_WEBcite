package com.filimonov.recipe.website_1.controllers;

import com.filimonov.recipe.website_1.model.Ingredient;
import com.filimonov.recipe.website_1.services.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
@RequestMapping("/ingredients")
@Tag(name = "Ингредиенты", description = "Управление ингредиентами для рецептов")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController (IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    @Operation(summary = "Добавление нового ингредиента", description = "Добавляет ингредиент и сохраняет его")
    public Ingredient addIngredient (@RequestBody Ingredient newIngredient) {
        if (StringUtils.isBlank(newIngredient.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Название не может быть пустым!");
        }
        return ingredientService.addIngredient(newIngredient);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Поиск ингердиента по id")
    public Ingredient getIngredient (@PathVariable int id) {
        Ingredient ingredient = ingredientService.getIngredient(id);
        if (ingredient == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ингредиент не найден");
        }
        return ingredient;
    }

    @GetMapping
    @Operation(summary = "Получение списка всех ингредиентов")
    public Collection<Ingredient> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактирование ингредиента по id")
    public Ingredient editIngredient(@PathVariable int id, @RequestBody Ingredient ingredient) {
        if (StringUtils.isBlank(ingredient.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Название не может быть пустым!");
        }
        return ingredientService.editIngredient(id, ingredient);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление ингредиента")
    public Ingredient deleteIngredient(@PathVariable int id) {
        Ingredient deleted = ingredientService.deleteIngredient(id);
        if (deleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ингредиент не найден для удаления");
        }
        return deleted;
    }

}
