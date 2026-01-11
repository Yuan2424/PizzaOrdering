public class PizzaSauce implements UserDisplayable{
    public float price;
    public String name;
    public String getDisplayName(){
        return name;
    }
    public PizzaSauce(String sauceName) {
        name = sauceName;
    }
    public PizzaSauce(String sauceName, float saucePrice) {
        name = sauceName;
        price = saucePrice;
    }
}
