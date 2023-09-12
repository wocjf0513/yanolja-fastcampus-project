package kdt_y_be_toy_project1.common.util.input;

public class Precondition {

    private Precondition() { }

    public static void require(boolean result, String message) {
        if (!result) {
            throw new InputException(message);
        }
    }
}
