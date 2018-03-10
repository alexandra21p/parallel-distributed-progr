import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by Alexandra on 05/11/2017.
 */
public class CustomFutureTask extends FutureTask<Integer> {

    private Integer row;

    public CustomFutureTask(Callable<Integer> callable, Integer row) {
        super(callable);
        this.row = row;
    }

    @Override
    protected void done() {

        SecondMultiplicationThread secondTask = new SecondMultiplicationThread( row );
        FutureTask secondFutureTask = new FutureTask( secondTask );
        //Main.getSecondFutures().add( secondFutureTask );
        Integer updatedCounter = Main.getCounter();
        updatedCounter++;
        Main.setCounter( updatedCounter );
        Main.getSecondExecutor().submit( secondFutureTask );

        if ( Main.getCounter() == Main.getFirstMatrix().length ) {
            Main.getSecondExecutor().shutdown();
        }
        try {
            secondFutureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


    }
}
