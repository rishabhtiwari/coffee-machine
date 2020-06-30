package coffeeMachine.models;

public enum  ExceptionMessage {
    NOT_PRESENT_INGREDIENT {
        @Override
        public String toString () {
            return "NOT_PRESENT_INGREDIENT";
        }
    },
    INSUFFICIENT_INGREDIENT {
        @Override
        public String toString () {
            return "INSUFFICIENT_INGREDIENT";
        }
    }
}
