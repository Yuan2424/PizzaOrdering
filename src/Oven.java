import java.util.ArrayList;

public interface Oven {
    public loadStatus loadPizza(Joint<Pizza, Integer> pie);
    public ArrayList<Pizza> unloadPizzas(int currentTime);
}
