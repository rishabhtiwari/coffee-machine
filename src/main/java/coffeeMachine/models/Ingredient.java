package coffeeMachine.models;

public class Ingredient {

    private IngredientType type;
    private Integer quantity;

    public Ingredient(IngredientType type, Integer quantity) {
        this.type = type;
        this.quantity = quantity;
    }

    public IngredientType getType() {
        return type;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
