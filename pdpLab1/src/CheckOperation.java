/**
 * Created by Alexandra on 22/10/2017.
 */
public class CheckOperation implements Runnable {
    @Override
    public void run() {
        Integer totalBillAmount = 0;
        Integer totalBillItems = 0;

        synchronized ( StoreInventory.getStock() ) {
            for (Bill bill : StoreInventory.getBills()) {
                totalBillAmount += bill.getTotalPrice();
                for (Product item : bill.getSoldItemslist()) {
                    totalBillItems += item.getQuantity();
                }
            }

            boolean checkMoney = totalBillAmount.equals(StoreInventory.getTotalAmount());
            boolean checkNumberOfItemsSold = totalBillItems.equals(StoreInventory.getNumberOfSoldItems());
            String numberOfItemsString = "\nAre the sold products justified by the bills? ( items sold from inventory = " + StoreInventory.getNumberOfSoldItems() +
                    " vs. items from bills =  " + totalBillItems + " ) " + String.valueOf(checkNumberOfItemsSold);
            System.out.println(numberOfItemsString);
            String moneyString = "What about the amount of money? ( money from the cash register = " + StoreInventory.getTotalAmount() +
                    " vs. amount of money from bills =  " + totalBillAmount + " ) " + String.valueOf(checkMoney);
            System.out.println(moneyString + "\n");
        }
    }
}
