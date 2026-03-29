import java.util.Random;

public class Simulator {
    int currentTime = 0;
    Random rand;
    Restaurant restaurant;
    LoadEquation equation;
    public Simulator(Restaurant r, LoadEquation equation) {
        restaurant = r;
        rand = new Random();
        this.equation = equation;
    }
    public void simulateDay() {
        restaurant.addOven(new DualOven()); //Think about new project and work on using the new loadEquation method in the simulator
        restaurant.addOven(new LongOven(5));
        restaurant.addOven(new ChainOven(10, 14, 10));
        while (currentTime < 720) {
            int n = equation.load(currentTime);
            loadNPizzas(n);
            if (n > 0) {
                System.out.print(" ");
            }
            currentTime++;
            restaurant.advanceQueue(currentTime);
            System.out.println(currentTime);
            System.out.println("Number of pizzas: " + restaurant.pizzas.size());
            System.out.println("Number of scheduled pizzas: " + restaurant.scheduledPizzas.size());
            System.out.println("Number of unloaded pizzas: " + restaurant.getNumUnloadPizzas());
        }
    }

    private void loadNPizzas(int n) {
        for (int i = 0; i < n; i++) {
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
        }
    }
}
