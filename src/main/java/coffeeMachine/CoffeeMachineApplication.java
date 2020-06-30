package coffeeMachine;

import coffeeMachine.models.BeverageType;
import coffeeMachine.models.IngredientType;
import coffeeMachine.service.CoffeeMachineService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;

@SpringBootApplication
public class CoffeeMachineApplication implements CommandLineRunner {

    @Autowired
    private CoffeeMachineService CoffeeMachineService;
    private static Logger logger;

    public static void main(String[] args) {
        SpringApplication.run(CoffeeMachineApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        this.CoffeeMachineService.getBeverage(BeverageType.COFFEE, new HashMap<IngredientType, Integer>(){{
            put(IngredientType.HOT_WATER, 100);
            put(IngredientType.MILK, 100);
            put(IngredientType.SUGAR, 20);
        }});
    }

}
