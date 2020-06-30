package coffeeMachine.service;

import coffeeMachine.models.Beverage;
import coffeeMachine.models.BeverageType;
import coffeeMachine.models.IngredientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

@Component
public class CoffeeMachineService {

    private BeverageService beverageService;
    private IngredientService ingredientService;


    @Autowired
    public CoffeeMachineService (BeverageService beverageService, IngredientService ingredientService, TaskExecutor taskExecutor) {
        this.beverageService = beverageService;
        this.ingredientService = ingredientService;
    }

    public Future getBeverage (BeverageType beverageType, Map<IngredientType, Integer> ingredientTypeWithQuantity) {
        return this.beverageService.submitBeverage(beverageType, ingredientTypeWithQuantity);
    }


    public void fillIngredient (IngredientType ingredientType, Integer quantity) {
        this.ingredientService.addIngredientQuantity (ingredientType, quantity);
    }

    public List<IngredientType> listOfAllAvailableIngredient () {
        return this.ingredientService.getAllAvailableIngredient();
    }

    public void init () {
        this.fillIngredient(IngredientType.HOT_WATER, 100);
        this.fillIngredient(IngredientType.MILK, 100);
        this.fillIngredient(IngredientType.SUGAR, 100);
    }

    public Integer getIngredientQuantity (IngredientType ingredientType) {
        return this.ingredientService.getIngredientQuantity(ingredientType);
    }
}
