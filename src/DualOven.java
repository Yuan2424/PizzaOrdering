import java.util.ArrayList;

public class DualOven implements Oven {
    Joint<Pizza, Integer> p1;
    Joint<Pizza, Integer> p2;

    public loadStatus loadPizza(Joint<Pizza, Integer> pie) {
        if (p1 == null) {
            p1 = pie;
            return loadStatus.successful;
        }
        else if (p2 == null) {
            p2 = pie;
            return loadStatus.successful;
        }
        return loadStatus.isFull;
    }

    public ArrayList<Pizza> unloadPizzas(int currentTime) {
        ArrayList<Pizza> pizzas = new ArrayList<>();
        int cookTime;
        if (p1 != null) {
            cookTime = p1.object1.toppings.size() + 10;
            if (p1.object2 + cookTime >= currentTime) {
                pizzas.add(p1.object1);
                p1 = null;
            }
        }
        if (p2 != null) {
            cookTime = p2.object1.toppings.size() + 10;
            if (p2.object2 + cookTime >= currentTime) {
                pizzas.add(p2.object1);
                p2 = null;
            }
        }
        return pizzas;
    }
}
