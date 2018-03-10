import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandra on 21/10/2017.
 */
public class StoreInventory {
    private static List<Product> stock = initializeStore();
    private static Integer totalAmount = 0;
    private static List<Bill> bills = new ArrayList<>();
    private static Integer itemsSold = 0;

    public static List<Product> getStock() {
        return stock;
    }

    public static Integer getTotalAmount() {
        return totalAmount;
    }

    public static List<Bill> getBills() {
        return bills;
    }

    public static void addBill(Bill bill) {
        bills.add(bill);
    }

    public static Integer getProductCount() {
        return stock.size();
    }

     public static Integer getNumberOfSoldItems() {
         return itemsSold;
     }

    public static Product getProduct(Integer index) {
        return stock.get(index);
    }

    public static void sellItem(Integer index, Integer quantity) {
        Product item = stock.get(index);
        Integer currentQuantity = item.getQuantity();
        item.setQuantity(currentQuantity - quantity);
        itemsSold += quantity; // used in integrity check operation
        increaseAmountOfMoney(item.getPrice() * quantity);
    }

    public static void increaseAmountOfMoney(Integer cash) {
        totalAmount += cash;
    }

    public static String showAvailableStock() {
        String result = "Available stock: ";
        for(Product p : stock) {
            result += p.showQuantity() + ", ";
        }
        result += "total amount of money = " + totalAmount;
        return result;
    }
    public static List<Product> initializeStore() {
        List<Product> stock = new ArrayList<>();

        stock.add(new Product(225, 5));
        stock.add(new Product(150, 11));
        stock.add(new Product(69, 2));
        stock.add(new Product(121, 8));
        stock.add(new Product(210, 15));

        return stock;
    }

    public static boolean isStockEmpty() {
        Integer stockSize = 0;
        for (Product product : stock) {
            stockSize += product.getQuantity();
        }

        return stockSize == 0;
    }
}
