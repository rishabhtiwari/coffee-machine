package coffeeMachine.models;

public enum  IngredientType {
    HOT_WATER {
        @Override
        public String toString() {
            return "HOT_WATER";
        }
    },
    SUGAR {
        @Override
        public String toString() {
            return "SUGAR";
        }
    },
    MILK {
        @Override
        public String toString() {
            return "MILK";
        }
    }
}
