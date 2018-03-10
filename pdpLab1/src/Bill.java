import java.util.ArrayList;
import java.util.List;


/**
 * Created by Alexandra on 21/10/2017.
 */
public class Bill {
    private List<Product> soldItemsList = new ArrayList<>();
    private Integer totalPrice = 0;

    public List<Product> getSoldItemslist() {
        return soldItemsList;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void addSoldItem(Product item) {
        soldItemsList.add(item);
    }

    public void addPrice(Integer price) {
        totalPrice += price;
    }
}
