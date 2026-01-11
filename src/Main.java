import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ArrayList<Topping> nonVeg = new ArrayList<Topping>(Arrays.asList(new Topping(0.05f, "pepperoni"), new Topping(0.1f, "sausage")));
        ArrayList<Topping> veg = new ArrayList<Topping>(Arrays.asList(new Topping(0.05f, "mushroom"), new Topping(0.07f, "olive")));
        Restaurant restaurantOne = new Restaurant(nonVeg, veg);
        restaurantOne.sizesList(new ArrayList<PizzaSize>(Arrays.asList(new PizzaSize("Medium", 10), new PizzaSize("Large", 13))));
        restaurantOne.crustsList(new ArrayList<CrustType>(Arrays.asList(new CrustType("Thin"), new CrustType("Stuffed"))));
        restaurantOne.saucesList(new ArrayList<PizzaSauce>(Arrays.asList(new PizzaSauce("Tomato", 2), new PizzaSauce("Barbeque", 2))));
        Simulator sim = new Simulator(restaurantOne);
        sim.simulateDay();
        Scanner scan = new Scanner(System.in);
        restaurantOne.pizzas.add(restaurantOne.makePizza(scan));
        restaurantOne.printPizza(restaurantOne.pizzas.get(0));

        Scanner scan2 = new Scanner(System.in);
        restaurantOne.schedulePizzaUI(scan2, 540);
        System.out.println(restaurantOne.advanceQueue(520));
    }
}