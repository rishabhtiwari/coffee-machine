package coffeeMachine.service;

import coffeeMachine.models.Beverage;
import coffeeMachine.models.BeverageType;
import coffeeMachine.models.Ingredient;
import coffeeMachine.models.IngredientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Future;

@Component
public class BeverageService {

    private IngredientService ingredientService;
    private TaskExecutor taskExecutor;
    private Queue <Future> runningtaskQueue;
    private Queue <Beverage> completedtaskQueue;


    @Autowired
    public BeverageService (IngredientService ingredientService, TaskExecutor taskExecutor) {
        this.ingredientService = ingredientService;
        this.taskExecutor = taskExecutor;
        this.runningtaskQueue = new LinkedList<>();
        this.completedtaskQueue = new LinkedList<>();
    }

//    public Beverage getBeverage (BeverageType beverageType, Map<IngredientType, Integer> ingredientTypeWithQuantity)  {
//        while (true) {
//            for (Beverage beverage : completedtaskQueue) {
//                if (this.isRequiredBeverage(beverage, beverageType, ingredientTypeWithQuantity)) {
//                    return beverage;
//                }
//            }
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public Future submitBeverage (BeverageType beverageType, Map<IngredientType, Integer> ingredientTypeWithQuantity) {
        return this.taskExecutor.submitTasks(() -> this.prepareBeverage(beverageType, ingredientTypeWithQuantity));
    }

    public Beverage prepareBeverage (BeverageType beverageType, Map<IngredientType, Integer> ingredientTypeWithQuantity) {
        try {
            List <Ingredient> ingredients = this.ingredientService.getIngredients(ingredientTypeWithQuantity);
            return new Beverage(beverageType, ingredients);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

//    public void movePreparedBeverageToCompletedQueue () {
//        new Thread(() -> {
//            try {
//                for (Future task: this.runningtaskQueue) {
//                    if (task.isDone()) {
//                        this.completedtaskQueue.add((Beverage) task.get());
//                        this.runningtaskQueue.remove(task);
//                    }
//                }
//                Thread.sleep(1000);
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }
//
//    public boolean isRequiredBeverage (Beverage beverage, BeverageType beverageType, Map<IngredientType, Integer> ingredientTypeWithQuantity) {
//        if (beverage.getName() != beverageType) return false;
//        else {
//            for (Ingredient ingredient: beverage.getIngredients()) {
//                if (!ingredient.getQuantity().equals(ingredientTypeWithQuantity.get(ingredient.getType()))) {
//                    return false;
//                }
//                ingredientTypeWithQuantity.remove(ingredient.getType());
//            }
//            return ingredientTypeWithQuantity.size() == 0;
//        }
//    }
}
