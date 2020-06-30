package CoffeeMachine;

import coffeeMachine.models.*;
import coffeeMachine.service.BeverageService;
import coffeeMachine.service.CoffeeMachineService;
import coffeeMachine.service.IngredientService;
import coffeeMachine.service.TaskExecutor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {CoffeeMachineService.class,TaskExecutor.class, BeverageService.class, IngredientService.class, IngredientRegistry.class})
public class CoffeeMachineTest {

    @Autowired
    private CoffeeMachineService coffeeMachineService;

    @Autowired
    private TaskExecutor taskExecutor;

    /*
    taskExecutor.init defining pool size
    coffeeMachineService.init initalize quantity of ingredients
     */
    @Before
    public void init() {
        taskExecutor.init(10);
        coffeeMachineService.init();
    }

    @Test
    public void successFullyServeBeverageTestCase () throws ExecutionException, InterruptedException {
        Map <IngredientType, Integer> ingredientWithQuantity =  new HashMap<IngredientType, Integer>() {{
            put(IngredientType.SUGAR, 20);
            put(IngredientType.HOT_WATER, 30);
            put(IngredientType.MILK, 100);
        }};
        Beverage beverage = (Beverage) this.coffeeMachineService.getBeverage(BeverageType.COFFEE,ingredientWithQuantity).get();
        Assert.isTrue(beverage.getName() == BeverageType.COFFEE, "BeverageType must be COFFEE");
        for (Map.Entry<IngredientType, Integer> ingredientEntry: ingredientWithQuantity.entrySet()) {
        boolean test = false;
        for (Ingredient ingredient : beverage.getIngredients()) {
            if (ingredientEntry.getKey() == ingredient.getType()) {
                if (ingredient.getQuantity().equals(ingredientEntry.getValue())) test = true;
            }
        }
        Assert.isTrue(test, ingredientEntry.getKey()  + "quantity must match");
    }
}

    @Test
    public void shortIngredientToServeBeverageTestCase () throws ExecutionException, InterruptedException {
        Beverage beverage = (Beverage) this.coffeeMachineService.getBeverage(BeverageType.COFFEE, new HashMap<IngredientType, Integer>() {{
            put(IngredientType.SUGAR, 150);
            put(IngredientType.HOT_WATER, 30);
            put(IngredientType.MILK, 100);
        }}).get();
        Assert.isTrue(beverage == null, "BeverageType can't serve");
    }

    @Test
    public void fillIngredientAndServeBeverageTestCase () throws ExecutionException, InterruptedException {
        Map <IngredientType, Integer> ingredientWithQuantity =  new HashMap<IngredientType, Integer>() {{
            put(IngredientType.SUGAR, 150);
            put(IngredientType.HOT_WATER, 30);
            put(IngredientType.MILK, 100);
        }};
        this.coffeeMachineService.fillIngredient(IngredientType.SUGAR, 50);
        Beverage beverage = (Beverage) this.coffeeMachineService.getBeverage(BeverageType.COFFEE, ingredientWithQuantity).get();
        for (Map.Entry<IngredientType, Integer> ingredientEntry: ingredientWithQuantity.entrySet()) {
            boolean test = false;
            for (Ingredient ingredient : beverage.getIngredients()) {
                if (ingredientEntry.getKey() == ingredient.getType()) {
                    if (ingredient.getQuantity().equals(ingredientEntry.getValue())) test = true;
                }
            }
            Assert.isTrue(test, ingredientEntry.getKey() + "quantity must match");
        }
    }

    @Test
    public void fillIngredientQuantityTestCase () {
        Integer expectedValue = this.coffeeMachineService.getIngredientQuantity (IngredientType.SUGAR) + 50;
        this.coffeeMachineService.fillIngredient(IngredientType.SUGAR, 50);
        Assert.isTrue(expectedValue.equals(this.coffeeMachineService.getIngredientQuantity(IngredientType.SUGAR)), "Quantity must match");
    }
}
