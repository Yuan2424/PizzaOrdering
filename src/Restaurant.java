import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Restaurant {
    // Methods: calculate price, cooking time, make new pizza
    // Variables: pizzas, toppings (vegetarian and non-vegetarian)
    public ArrayList<Pizza> pizzas;
    public ArrayList<Joint<Pizza, Integer>> scheduledPizzas;
    public ArrayList<Pizza> unloadedPizzas;
    public ArrayList<Oven> ovens;
    public ArrayList<Topping> toppingsNonVeg;
    public ArrayList<Topping> toppingsVeg;
    public ArrayList<PizzaSize> sizes;
    public ArrayList<CrustType> crusts;
    public ArrayList<PizzaSauce> sauces;
    int maxToppings = 10;
    int lastPassedTime = -1;
    int numUnloadPizzas = 0;
    public Restaurant() {
        pizzas = new ArrayList<>();
        scheduledPizzas = new ArrayList<>();
        unloadedPizzas = new ArrayList<>();
        ovens = new ArrayList<>();
        toppingsNonVeg = new ArrayList<>();
        toppingsVeg = new ArrayList<>();
        sizes = new ArrayList<>();
        crusts = new ArrayList<>();
        sauces = new ArrayList<>();
    }
    public Restaurant(ArrayList<Topping> nonVeg, ArrayList<Topping> veg) {
        pizzas = new ArrayList<>();
        scheduledPizzas = new ArrayList<>();
        unloadedPizzas = new ArrayList<>();
        ovens = new ArrayList<>();
        toppingsNonVeg = nonVeg;
        toppingsVeg = veg;
        sizes = new ArrayList<>();
        crusts = new ArrayList<>();
        sauces = new ArrayList<>();
    }
    public static Restaurant makeRestaurant() {
        return new Restaurant();
    }
    public void addOven(Oven oven) {
        this.ovens.add(oven);
    }
    public void sizesList(ArrayList<PizzaSize> sizes) {
        this.sizes = sizes;
    }
    public void crustsList(ArrayList<CrustType> crusts) {
        this.crusts = crusts;
    }
    public void saucesList(ArrayList<PizzaSauce> sauces) {
        this.sauces = sauces;
    }
    public Topping selectTopping(Scanner scan) {
        System.out.println("The non-vegetarian toppings available are: ");
        for (Topping i : toppingsNonVeg) {
            System.out.println(i.name);
        }
        System.out.println("The vegetarian toppings available are: ");
        for (Topping i : toppingsVeg) {
            System.out.println(i.name);
        }
        int selection = scan.nextInt();
        if (selection == 0) {
            return null;
        }
        else if (selection <= toppingsNonVeg.size()) {
            return toppingsNonVeg.get(selection - 1);
        }
        return toppingsVeg.get(selection - toppingsNonVeg.size() - 1);
    }
    public <t extends UserDisplayable> t selectItem(Scanner scan, ArrayList<t> list) {
        System.out.println("The options are: ");
        for (t i : list) {
            System.out.println(i.getDisplayName());
        }
        int choice = scan.nextInt();
        if (choice == 0 || choice > list.size()) {
            return null;
        }
        else {
            return list.get(choice - 1);
        }
    }
    public Pizza makePizza(Scanner scan) {
        boolean toppingsDone = false;
        ArrayList<Topping> toppings = new ArrayList<>();
        Scanner toppingScan = new Scanner(System.in);
        while (!toppingsDone) {
            Topping newTopping = selectTopping(toppingScan);
            if (newTopping != null) {
                toppings.add(newTopping);
            }
            else {
                toppingsDone = true;
            }
        }
        CrustType crust = selectItem(scan, crusts);
        PizzaSauce sauce = selectItem(scan, sauces);
        PizzaSize size = selectItem(scan, sizes);
        Pizza newPizza = new Pizza(size, crust, sauce, toppings);
        return newPizza;
        //this.pizzas.add(newPizza);
    }
    public Pizza makePizza(Random rand){
        ArrayList<Topping> toppings = new ArrayList<>();
        int toppingNum = Math.abs(rand.nextInt()) % maxToppings;
        for (int i = 0; i < toppingNum; i++) {
            toppings.add(toppingsNonVeg.get(Math.abs(rand.nextInt()) % toppingsNonVeg.size()));
        }
        CrustType crust = crusts.get(Math.abs(rand.nextInt()) % crusts.size());
        PizzaSauce sauce = sauces.get(Math.abs(rand.nextInt()) % sauces.size());
        PizzaSize size = sizes.get(Math.abs(rand.nextInt()) % sizes.size());
        return new Pizza(size, crust, sauce, toppings);
    }
    public float calculatePrice(Pizza currentPizza) {
        float price = 0;
        price += currentPizza.sauce.price + currentPizza.size.price;
        for (Topping item : currentPizza.toppings) {
            price += item.price;
        }
        return price;
    }
    public void printPizza(Pizza currentPizza) {
        System.out.println(currentPizza.crust.name + " crust");
        System.out.println(currentPizza.sauce.name + " sauce");
        System.out.println(currentPizza.size.name + " sized pizza");
        System.out.print("Toppings: ");
        for (Topping item : currentPizza.toppings) {
            System.out.print(item.name + ", ");
        }
        System.out.println();
        System.out.println("Your total is $" + calculatePrice(currentPizza));
        System.out.println("Please wait " + waitTime(currentPizza) + " minutes");
    }
    public int pizzaCookTime(Pizza currentPizza) {
        int time = 10;
        time += currentPizza.toppings.size();
        return time;
    }
    public float waitTime(Pizza currentPizza) {
        float time = 0;
        for (Pizza x : pizzas) {
            if (!x.equals(currentPizza)) {
                time += pizzaCookTime(x);
            }
        }
        time += pizzaCookTime(currentPizza);
        return time;
    }
    public void schedulePizzaUI(Scanner scan, Integer time) {
        Pizza pizza = makePizza(scan);
        schedulePizza(time, pizza);
    }

    public void schedulePizza(Integer time, Pizza pizza) {
        Joint<Pizza, Integer> scheduled = new Joint<>(pizza, time);
        this.scheduledPizzas.add(scheduled);
    }

    // advanceQueue should take in a time and choose a pizza to cook. The pizza will be printed and the new time after cooking is returned.
    // advanceQueue cannot use the same time twice
    public void advanceQueue(Integer currentTime) throws IllegalArgumentException {
        if (lastPassedTime >= currentTime) {
            throw(new IllegalArgumentException());
        }
        for (Oven o : ovens) {
            o.unloadPizzas(currentTime);
        }
        loadStatus status = loadStatus.successful;
        int start = 0;
        while (!scheduledPizzas.isEmpty() && status == loadStatus.successful) {
            int cookTime = pizzaCookTime(scheduledPizzas.get(start).object1);
            int scheduledTime = scheduledPizzas.get(start).object2;
            if (cookTime + currentTime >= scheduledTime) {
                Joint<Pizza, Integer> timedPizza = scheduledPizzas.get(start).cloneJoint();
                timedPizza.object2 = currentTime;
                status = attemptLoad(timedPizza);
                if (status == loadStatus.successful) {
                    scheduledPizzas.remove(start);
                    numUnloadPizzas++;
                }
                else if (status == loadStatus.couldNotLoad) {
                    start++;
                }
            }
            else {
                break;
            }
        }
        start = 0;
        while (!pizzas.isEmpty() && status == loadStatus.successful) {
            Joint<Pizza, Integer> pizza = new Joint<>(pizzas.get(start), currentTime);
            status = attemptLoad(pizza);
            if (status == loadStatus.successful) {
                pizzas.remove(start);
                numUnloadPizzas++;
            }
            else if (status == loadStatus.couldNotLoad) {
                start++;
            }
        }
        lastPassedTime = currentTime;
    }
    public loadStatus attemptLoad(Joint<Pizza, Integer> pizza) {
        int num = 0;
        for (Oven o : ovens) {
            loadStatus temp = o.loadPizza(pizza);
            if (temp == loadStatus.successful) {
                return loadStatus.successful;
            }
            else if (temp == loadStatus.couldNotLoad) {
                num++;
            }
        }
        if (num > 0) {
            return loadStatus.couldNotLoad;
        }
        return loadStatus.isFull;
    }
    public int getNumUnloadPizzas() {
        return numUnloadPizzas;
    }
    //public void selectPizza()
}
