/**
 * Created by Alexandra on 21/10/2017.
 */
public class Product {
    private Integer quantity;
    private Integer price;

    public Product(Integer quantity, Integer price) {
        this.quantity = quantity;
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product {" +
                "quantity = " + quantity +
                ", price = " + price +
                '}';
    }

    public String showQuantity() {
        return "Product { quantity = " + quantity + "}";
    }
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
