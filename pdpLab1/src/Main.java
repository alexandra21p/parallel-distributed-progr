import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandra on 21/10/2017.
 */
public class Main {
    //private static final int NTHREDS = 5;

    public static void main(String[] args) throws InterruptedException {

        List<Thread> threads = new ArrayList<>();
        System.out.println("\t\tINITIAL STORE INVENTORY: \n");
        System.out.println(StoreInventory.showAvailableStock());
        System.out.println("\n____________________________________________________________________________________________\n");

        for (int i = 0; i < 20; i++) {
            threads.add(new Thread(new SaleOperation()));
        }

        for (int i = 0; i < 20; i++) {
            threads.get(i).start();
            if (i % 2 == 0) {
                new Thread(new CheckOperation()).start();
                threads.get(i).join(); // pause check operation until sale operation finishes
            }
        }

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("____________________________________________________________________\n");
        System.out.println("The other threads have finished. Main thread will do a final check..");
        System.out.println("____________________________________________________________________");

//        ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
//        for (int i = 0; i < 20; i++) {
//            Runnable worker = new SaleOperation();
//            executor.execute(worker);
//        }
//
//        executor.shutdown();
//        // Wait until all threads are finished
//        try {
//            executor.awaitTermination(1, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Finished all threads");



        new Thread(new CheckOperation()).start();
    }
}
