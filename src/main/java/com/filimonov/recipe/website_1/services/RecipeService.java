package com.filimonov.recipe.website_1.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.filimonov.recipe.website_1.model.Ingredient;
import com.filimonov.recipe.website_1.model.Recipe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecipeService {
    @Autowired
    private FileService fileService;

    @Value("${data.file.name.recipes}")
    private String recipesFileName;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Map<Integer, Recipe> recipeMap = new HashMap<>();
    private static int idCounter = 1;

    public Recipe addRecipe(Recipe newRecipe) {
        newRecipe.setId(idCounter);
        recipeMap.put(idCounter++, newRecipe);
        saveToFile();
        return newRecipe;
    }

    public Recipe getRecipe(int id) {
        return recipeMap.get(id);
    }

    public Collection<Recipe> getAllRecipes() {
        return recipeMap.values();
    }

    public Recipe editRecipe(int id, Recipe recipe) {
        if (recipeMap.containsKey(id)) {
            recipe.setId(id);
            recipeMap.put(id, recipe);
            saveToFile();
            return recipe;
        }
        return null;
    }

    public Recipe deleteRecipe(int id) {
        Recipe removedRecipe = recipeMap.remove(id);
        saveToFile();
        return removedRecipe;
    }

    public Collection<Recipe> searchByIngredient(int ingredientId) {
        List<Recipe> foundRecipes = new ArrayList<>();
        for (Recipe recipe : recipeMap.values()){
            for (Ingredient ingredient : recipe.getIngredients()){
                if (ingredient.getId() == ingredientId) {
                    foundRecipes.add(recipe);
                    break;
                }
            }
        }
        return foundRecipes;
    }

    public Collection<Recipe> searchByIngredients(List<String> names) {
        List<Recipe> result = new ArrayList<>();
        List<String> searchNames = new ArrayList<>();
        for (String s : names) {
            searchNames.add(s.toLowerCase());
        }

        for (Recipe recipe : recipeMap.values()) {
            List<String> recipeIngredientNames = new ArrayList<>();
            for (Ingredient ing : recipe.getIngredients()) {
                recipeIngredientNames.add(ing.getName().toLowerCase());
            }

            if (recipeIngredientNames.containsAll(searchNames)) {
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

    private void saveToFile() {
        try {
            String json = objectMapper.writeValueAsString(recipeMap);
            fileService.saveToFile(json, recipesFileName);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    private void init() {
        try {
            String json = fileService.readFromFile(recipesFileName);
            if (json != null && !json.isEmpty()) {
                Map<Integer, Recipe> loadedMap = objectMapper.readValue(
                        json,
                        new TypeReference<HashMap<Integer, Recipe>>() {}
                );
                recipeMap.putAll(loadedMap);
                if (!loadedMap.isEmpty()) {
                    idCounter = Collections.max(loadedMap.keySet()) + 1;
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
