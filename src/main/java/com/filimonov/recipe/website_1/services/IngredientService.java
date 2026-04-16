package com.filimonov.recipe.website_1.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.filimonov.recipe.website_1.model.Ingredient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientService {
    @Autowired
    private FileService fileService;

    @Value("${data.file.name.ingredients}")
    private String ingredientsFileName;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<Integer, Ingredient> ingredientMap = new HashMap<>();
    private static int idCounter = 1;

    public Ingredient addIngredient (Ingredient newIngredient){
        newIngredient.setId(idCounter);
        ingredientMap.put(idCounter++, newIngredient);
        saveToFile();
        return newIngredient;
    }

    public Ingredient getIngredient (int id) {
        return ingredientMap.get(id);
    }

    public Collection<Ingredient> getAllIngredients() {
        return ingredientMap.values();
    }

    public Ingredient editIngredient(int id, Ingredient ingredient) {
        if (ingredientMap.containsKey(id)) {
            ingredient.setId(id);
            ingredientMap.put(id, ingredient);
            saveToFile();
            return ingredient;
        }
        return null;
    }

    public Ingredient deleteIngredient(int id) {
        Ingredient removedIngredient = ingredientMap.remove(id);
        saveToFile();
        return removedIngredient;
    }

    private void saveToFile() {
        try {
            String json = objectMapper.writeValueAsString(ingredientMap);
            fileService.saveToFile(json, ingredientsFileName);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    private void init() {
        try {
            String json = fileService.readFromFile(ingredientsFileName);
            if (json != null && !json.isEmpty()) {
                Map<Integer, Ingredient> loadedMap = objectMapper.readValue(
                        json,
                        new TypeReference<HashMap<Integer, Ingredient>>() {
                        }
                );
                ingredientMap.putAll(loadedMap);
                if (!loadedMap.isEmpty()) {
                    idCounter = Collections.max(loadedMap.keySet()) + 1;
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
