package coffeeMachine.service;

import coffeeMachine.models.ExceptionMessage;
import coffeeMachine.models.Ingredient;
import coffeeMachine.models.IngredientRegistry;
import coffeeMachine.models.IngredientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class IngredientService {

    private IngredientRegistry ingredientRegistry;

    @Autowired
    public IngredientService (IngredientRegistry ingredientRegistry) {
        this.ingredientRegistry = ingredientRegistry;
    }

    public List<Ingredient> getIngredients (Map<IngredientType, Integer> ingredientTypeWithQuantity) throws Exception {
        List<Ingredient> ingredients = new ArrayList<Ingredient>();

        for (Map.Entry<IngredientType, Integer> ingredient : ingredientTypeWithQuantity.entrySet()) {
            if (this.ingredientRegistry.canGetIngredientQuantity(ingredient)) {
                ingredients.add(new Ingredient(ingredient.getKey(), ingredient.getValue()));
            }
            else {
                throw new Exception(ingredient.getKey().toString() + " "  + ExceptionMessage.INSUFFICIENT_INGREDIENT.toString());
            }
        }
        return ingredients;
    }

    public List <IngredientType> getAllAvailableIngredient () {
        return this.ingredientRegistry.getAllAvailableIngredient();
    }

    public void addIngredientQuantity (IngredientType ingredientType, Integer quantity) {
        this.ingredientRegistry.addIngredientQuantity(ingredientType, quantity);
    }

    public Integer getIngredientQuantity (IngredientType ingredientType) {
        return this.ingredientRegistry.getIngredientQuantity(ingredientType);
    }
}
