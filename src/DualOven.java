import java.util.ArrayList;

public class DualOven implements Oven {
    Pizza p1;
    Pizza p2;

    public loadStatus loadPizza(Pizza pie) {
        if (p1 == null) {
            p1 = pie;
        }
        else if (p2 == null) {

        }
    }

    public ArrayList<Pizza> unloadPizzas() {
        return null;
    }
}
