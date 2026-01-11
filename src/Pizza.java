import java.util.ArrayList;

public class Pizza {
    public PizzaSize size;
    public CrustType crust;
    public PizzaSauce sauce;
    public ArrayList<Topping> toppings;
    public Pizza() {
        toppings = new ArrayList<Topping>();
    }
    public Pizza(PizzaSize pizzaSize, CrustType pizzaCrustType, PizzaSauce pizzaSauce, ArrayList<Topping> pizzaToppings) {
        size = pizzaSize;
        crust = pizzaCrustType;
        sauce = pizzaSauce;
        toppings = new ArrayList<Topping>(pizzaToppings);
    }
}
