import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Restaurant {
    // Methods: calculate price, cooking time, make new pizza
    // Variables: pizzas, toppings (vegetarian and non-vegetarian)
    public ArrayList<Pizza> pizzas;
    public ArrayList<Joint<Pizza, Integer>> scheduledPizzas;
    public ArrayList<Topping> toppingsNonVeg;
    public ArrayList<Topping> toppingsVeg;
    public ArrayList<PizzaSize> sizes;
    public ArrayList<CrustType> crusts;
    public ArrayList<PizzaSauce> sauces;
    int maxToppings = 10;
    public Restaurant() {
        pizzas = new ArrayList<Pizza>();
        scheduledPizzas = new ArrayList<Joint<Pizza, Integer>>();
        toppingsNonVeg = new ArrayList<Topping>();
        toppingsVeg = new ArrayList<Topping>();
        sizes = new ArrayList<PizzaSize>();
        crusts = new ArrayList<CrustType>();
        sauces = new ArrayList<PizzaSauce>();
    }
    public Restaurant(ArrayList<Topping> nonVeg, ArrayList<Topping> veg) {
        pizzas = new ArrayList<Pizza>();
        scheduledPizzas = new ArrayList<Joint<Pizza, Integer>>();
        toppingsNonVeg = nonVeg;
        toppingsVeg = veg;
        sizes = new ArrayList<PizzaSize>();
        crusts = new ArrayList<CrustType>();
        sauces = new ArrayList<PizzaSauce>();
    }
    public static Restaurant makeRestaurant() {
        Restaurant newRestaurant = new Restaurant();
        //newRestaurant.toppingsNonVeg =
        return newRestaurant;
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
        ArrayList<Topping> toppings = new ArrayList<Topping>();
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
        return(newPizza);
        //this.pizzas.add(newPizza);
    }
    public Pizza makePizza(Random rand){
        ArrayList<Topping> toppings = new ArrayList<Topping>();
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
        Joint<Pizza, Integer> scheduled = new Joint<Pizza, Integer>(pizza, time);
        this.scheduledPizzas.add(scheduled);
    }

    // advanceQueue should take in a time and choose a pizza to cook. The pizza will be printed and the new time after cooking is returned.
    public Integer advanceQueue(Integer currentTime) {
        if (!scheduledPizzas.isEmpty() && !pizzas.isEmpty()) {
            if (pizzaCookTime(scheduledPizzas.get(0).object1) + currentTime >= scheduledPizzas.get(0).object2) {
                printPizza(scheduledPizzas.get(0).object1);
                Pizza tempPizza = scheduledPizzas.remove(0).object1;
                return currentTime + pizzaCookTime(tempPizza);
            } else {
                printPizza(pizzas.get(0));
                Pizza tempPizza = pizzas.remove(0);
                return currentTime + pizzaCookTime(tempPizza);
            }
        }
        else if (!scheduledPizzas.isEmpty()) {
            if (pizzaCookTime(scheduledPizzas.get(0).object1) + currentTime >= scheduledPizzas.get(0).object2) {
                printPizza(scheduledPizzas.get(0).object1);
                Pizza tempPizza = scheduledPizzas.remove(0).object1;
                return currentTime + pizzaCookTime(tempPizza);
            }
        }
        else if (!pizzas.isEmpty()) {
            printPizza(pizzas.get(0));
            Pizza tempPizza = pizzas.remove(0);
            return currentTime + pizzaCookTime(tempPizza);
        }
        return currentTime;
    }
    //public void selectPizza()
}
