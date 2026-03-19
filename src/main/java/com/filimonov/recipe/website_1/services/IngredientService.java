package com.filimonov.recipe.website_1.services;

import com.filimonov.recipe.website_1.model.Ingredient;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientService {
    private final Map<Integer, Ingredient> ingredientMap = new HashMap<>();
    private static int idCounter = 1;

    public Ingredient addIngredient (Ingredient newIngredient){
        newIngredient.setId(idCounter);
        ingredientMap.put(idCounter++, newIngredient);
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
            return ingredient;
        }
        return null;
    }

    public Ingredient deleteIngredient(int id) {
        return ingredientMap.remove(id);
    }
}
