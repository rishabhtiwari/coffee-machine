package coffeeMachine.models;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class IngredientRegistry {

    private Map<IngredientType, Integer> ingredientWithQuantity;

    public IngredientRegistry () {
        this.ingredientWithQuantity = new HashMap<IngredientType, Integer>();
    }

    public synchronized void  addIngredientQuantity(IngredientType ingredientType, Integer quantity) {
        if (this.ingredientWithQuantity.containsKey(ingredientType)) {
            this.ingredientWithQuantity.put(ingredientType, quantity + this.ingredientWithQuantity.get(ingredientType));
        }
        else {
            this.ingredientWithQuantity.put(ingredientType, quantity);
        }
    }

    public synchronized Boolean canGetIngredientQuantity (Map.Entry<IngredientType, Integer> ingredient) {
        if (this.ingredientWithQuantity.containsKey(ingredient.getKey())) {
            Integer currentQuantity = this.ingredientWithQuantity.get(ingredient.getKey());
            if (currentQuantity >= ingredient.getValue()) {
                this.ingredientWithQuantity.put(ingredient.getKey(), this.ingredientWithQuantity.get(ingredient.getKey())- ingredient.getValue());
                return true;
            }
        }
        return false;
    }

    public synchronized List<IngredientType> getAllAvailableIngredient () {
        List <IngredientType> ingredientList = new ArrayList<IngredientType>();
        for (Map.Entry<IngredientType, Integer> ingredientEntry : ingredientWithQuantity.entrySet()) {
            if (ingredientEntry.getValue() > 0) ingredientList.add(ingredientEntry.getKey());
        }
        return ingredientList;
    }

    public synchronized Integer getIngredientQuantity (IngredientType ingredientType) {
        return this.ingredientWithQuantity.get(ingredientType);
    }
}
