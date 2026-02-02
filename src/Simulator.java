import java.util.Random;

public class Simulator {
    int currentTime = 0;
    Random rand;
    Restaurant restaurant;
    public Simulator(Restaurant r) {
        restaurant = r;
        rand = new Random();
    }
    public void simulateDay() {
        restaurant.addOven(new DualOven());
        while (currentTime < 720) {
            if (rand.nextBoolean()) {
                restaurant.schedulePizza(
                        currentTime + Math.abs(rand.nextInt()) % (720 - currentTime),
                        restaurant.makePizza(rand));
                restaurant.scheduledPizzas.sort((a, b) -> {
                    if (a.object2 < b.object2) {
                        return -1;
                    }
                    else if (a.object2 > b.object2) {
                        return 1;
                    }
                    return 0;
                });
            }
            else {
                restaurant.pizzas.add(restaurant.makePizza(rand));
            }
            currentTime++;
            restaurant.advanceQueue(currentTime);
            System.out.println(currentTime);
            System.out.println("Number of pizzas: " + restaurant.pizzas.size());
            System.out.println("Number of scheduled pizzas: " + restaurant.scheduledPizzas.size());
            System.out.println("Number of unloaded pizzas: " + restaurant.getNumUnloadPizzas());
        }
    }

}
