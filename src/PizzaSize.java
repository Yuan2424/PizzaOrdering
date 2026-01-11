public class PizzaSize implements UserDisplayable{
    public float price;
    public String name;
    public String getDisplayName() {
        return name;
    }
    public PizzaSize(String sizeName) {
        name = sizeName;
    }
    public PizzaSize(String sizeName, float sizePrice) {
        name = sizeName;
        price = sizePrice;
    }
}
