import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Alexandra on 21/10/2017.
 */
public class SaleOperation implements Runnable {
    public void run() {
        Integer saleOperationsPerThread = ThreadLocalRandom.current().nextInt(1, 10);
        //System.out.println("\n\tNUMBER OF OPERATIONS FOR CURRENT THREAD (" + Thread.currentThread().getName() +  ") : " + saleOperationsPerThread + "\n");

        for (int i = 0; i < saleOperationsPerThread; i++) {

            synchronized ( StoreInventory.getStock() ) {
                if ( StoreInventory.isStockEmpty()) {
                    System.out.println("Stock is empty. There are no more sale operations to be performed.");
                    return;
                }
                Integer productOptions = StoreInventory.getProductCount();
                Integer productNumber = ThreadLocalRandom.current().nextInt(0, productOptions);

                Bill bill = new Bill();

                //System.out.println("\n\tFOR CURRENT THREAD'S (" + Thread.currentThread().getName() +  ") OPERATION #" + String.valueOf(i) + "/" + saleOperationsPerThread + " THERE ARE #" + productNumber + " SALES\n");

                for (int j = 0; j < productNumber; j++) {

                    Integer index = ThreadLocalRandom.current().nextInt(0, productOptions);
                    Integer availableQuantity = StoreInventory.getProduct(index).getQuantity();
                    Integer unitPrice = StoreInventory.getProduct(index).getPrice();
                    if (availableQuantity > 0) {
                        Integer randomQuantity = ThreadLocalRandom.current().nextInt(0, availableQuantity+1);
                        StoreInventory.sellItem(index, randomQuantity);
                        bill.addSoldItem(new Product(randomQuantity, unitPrice));
                        bill.addPrice(unitPrice * randomQuantity);

                        System.out.println("Current thread with ID: #" + Thread.currentThread().getName() + " on product #" + index + " " +
                                StoreInventory.getProduct(index).toString() + ", quantity sold: " + randomQuantity);
                    }
                }
                StoreInventory.addBill(bill);
                System.out.println(StoreInventory.showAvailableStock());
            }
//            System.out.println(StoreInventory.showAvailableStock());
        }
    }
}
