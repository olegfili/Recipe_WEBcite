package com.filimonov.recipe.website_1.services;

import com.filimonov.recipe.website_1.model.Ingredient;
import com.filimonov.recipe.website_1.model.Recipe;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecipeService {
    private final Map<Integer, Recipe> recipeMap = new HashMap<>();
    private static int idCounter = 0;

    public Recipe addRecipe(Recipe newRecipe) {
        recipeMap.put(idCounter++, newRecipe);
        return newRecipe;
    }

    public Recipe getRecipe(int id) {
        return recipeMap.get(id);
    }

    public Collection<Recipe> getAllRecipes() {
        return recipeMap.values();
    }

    public Recipe editRecipe(int id, Recipe recipe) {
        return recipeMap.replace(id, recipe);
    }

    public Recipe deleteRecipe(int id) {
        return recipeMap.remove(id);
    }

    public Collection<Recipe> searchByIngredient(String name) {
        List<Recipe> foundRecipes = new ArrayList<>();
        for (Recipe recipe : recipeMap.values()){
            for (Ingredient ingredient : recipe.getIngredients()){
                if (ingredient.getName().equalsIgnoreCase(name) ){
                    foundRecipes.add(recipe);
                    break;
                }
            }
        }
        return foundRecipes;
    }

    public Collection<Recipe> searchByIngredients(List<String> names) {
        List<Recipe> result = new ArrayList<>();
        for (Recipe recipe : recipeMap.values()) {
            List<String> recipeIngredientNames = new ArrayList<>();
            for (Ingredient ing : recipe.getIngredients()) {
                recipeIngredientNames.add(ing.getName());
            }
            if (recipeIngredientNames.containsAll(names)) {
                result.add(recipe);
            }
        }
        return result;
    }

    public List<Recipe> getRecipesByPage(int page) {
        int size = 10;
        List<Recipe> allRecipes = new ArrayList<>(recipeMap.values());
        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, allRecipes.size());
        if (fromIndex >= allRecipes.size()) {
            return new ArrayList<>();
        }
        return allRecipes.subList(fromIndex, toIndex);
    }
}
