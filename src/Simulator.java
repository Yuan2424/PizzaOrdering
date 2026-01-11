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
        while (currentTime < 720) {
            if (rand.nextBoolean()) {
                restaurant.schedulePizza(
                        currentTime + Math.abs(rand.nextInt()) % (720 - currentTime),
                        restaurant.makePizza(rand));
            }
            else {
                restaurant.pizzas.add(restaurant.makePizza(rand));
            }
            currentTime = restaurant.advanceQueue(currentTime);
            System.out.println(currentTime);
        }
    }

}
