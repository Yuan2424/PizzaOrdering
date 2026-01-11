import java.util.ArrayList;

public interface Oven {
    public loadStatus loadPizza(Pizza pie);
    public ArrayList<Pizza> unloadPizzas();
}
