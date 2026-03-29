import java.util.ArrayList;

public class ChainOven implements Oven {
    private final int lowTime;
    private final int highTime;
    private final ArrayList<Joint<Pizza, Integer>> loadedPizzas;
    private final int maxNum;

    public ChainOven(int lTime, int hTime, int max) {
        lowTime = lTime;
        highTime = hTime;
        loadedPizzas = new ArrayList<>();
        maxNum = max;
    }

    public loadStatus loadPizza(Joint<Pizza, Integer> pie) {
        if (pie.object2 < lowTime || pie.object2 > highTime) {
            return loadStatus.couldNotLoad;
        }
        if (loadedPizzas.size() < maxNum) {
            loadedPizzas.add(pie);
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
