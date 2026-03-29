import java.util.ArrayList;

public class LongOven implements Oven{
    ArrayList<Joint<Pizza, Integer>> loadedPizzas;
    int maxNum;

    public LongOven(int max) {
        loadedPizzas = new ArrayList<>();
        maxNum = max;
    }

    public loadStatus loadPizza(Joint<Pizza, Integer> pizza) {
        if (loadedPizzas.size() < maxNum) {
            loadedPizzas.add(pizza);
            return loadStatus.successful;
        }
        return loadStatus.isFull;
    }

    public ArrayList<Pizza> unloadPizzas(int currentTime) {
        ArrayList<Pizza> unloaded = new ArrayList<>();
        for (int i = 0; i < loadedPizzas.size(); i++) {
            Joint<Pizza, Integer> p = loadedPizzas.get(i);
            int cookTime = p.object1.toppings.size() + 10;
            if (p.object2 + cookTime >= currentTime) {
                unloaded.add(p.object1);
                loadedPizzas.remove(p);
                i--;
            }
        }
        return unloaded;
    }
}
