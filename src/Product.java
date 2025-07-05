public class Product implements Shipping {
    private final String name;
    private double price;
    private int quantity;
    private double weight;
    private boolean isExpired;
    private boolean isShippable;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        isExpired = isShippable = false;
        weight = 0;
    }
    public Product(String name, double price, int quantity, double weight) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.weight = weight;
        isShippable = true;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public boolean isShippable() {
        return isShippable;
    }

    public int getQuantity() {
        return quantity;
    }

    public void makeShippable(double weight) {
        isShippable = true;
        this.weight = weight;
    }
    public void makeExpired() {
        isExpired = true;
    }
    public void addQuantity(int val) {
        quantity += val;
    }
    public void removeQuantity(int val) {
        if(quantity < val)
            throw new IllegalArgumentException("Not enough quantity of this product there is only " + quantity + "left!");
        quantity -= val;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getWeight() {
        if(!isShippable)
            throw new UnsupportedOperationException("This product is not shippable.");
        return weight;
    }
}

interface Shipping {
    String getName();
    double getWeight();
}

