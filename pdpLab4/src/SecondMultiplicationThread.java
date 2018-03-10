import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Alexandra on 04/11/2017.
 */
public class SecondMultiplicationThread implements Callable {
    private Integer row;

    public SecondMultiplicationThread( Integer row ) {
        this.row = row;
    }

    @Override
    public List<Long> call() {
        int commonCols = Main.getFirstResultMatrix()[0].length;
        int cols = Main.getThirdMatrix()[0].length;
        List<Long> result = new ArrayList<>();

//    synchronized (Main.getSecondResultMatrix()) {
        for (int i = 0; i < cols; i++) {
            Long val = 0L;
            for (int j = 0; j < commonCols; j++) {
                val += Main.getFirstResultMatrix()[row][j] * Main.getThirdMatrix()[j][i];
            }
            Main.getSecondResultMatrix()[row][i] = val;
            result.add( val );
        }
//    }

        return result;
    }
}
