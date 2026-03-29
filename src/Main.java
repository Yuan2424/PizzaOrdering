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
        LoadEquation equation = (time) -> {
            double result = (-2.3721527778 * Math.pow(10, -9) * Math.pow(time, 4) + 0.00000363181018519 * Math.pow(time, 3) - 0.00181444166667 * Math.pow(time, 2) + 0.309066 * time - 6);
            return (int)result;
        };
        Simulator sim = new Simulator(restaurantOne, equation);
        sim.simulateDay();
//        Scanner scan = new Scanner(System.in);
//        restaurantOne.pizzas.add(restaurantOne.makePizza(scan));
//        restaurantOne.printPizza(restaurantOne.pizzas.get(0));

//        Scanner scan2 = new Scanner(System.in);
//        restaurantOne.schedulePizzaUI(scan2, 540);
    }
}