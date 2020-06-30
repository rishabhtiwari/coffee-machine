package coffeeMachine.models;

import java.util.List;

public class Beverage {

    private BeverageType name;
    private List <Ingredient> ingredients;

    public Beverage(BeverageType name, List <Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public BeverageType getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}
