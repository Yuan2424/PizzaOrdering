public class CrustType implements UserDisplayable{
    public float price;
    public String name;
    public String getDisplayName(){
        return name;
    }
    public CrustType(String crustName) {
        name = crustName;
    }
}
