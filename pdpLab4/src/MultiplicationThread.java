import java.util.concurrent.Callable;

/**
 * Created by Alexandra on 29/10/2017.
 */
public class MultiplicationThread implements Callable {
    //private List<MatrixCell> slots = new ArrayList<>();
    private Integer row;

    public MultiplicationThread( Integer row ) {
        this.row = row;
    }

    @Override
    public Integer call() {
        int commonCols = Main.getFirstMatrix()[0].length;
        int cols = Main.getSecondMatrix()[0].length;
        Integer currentRow = row;

        synchronized (Main.getFirstResultMatrix()) {
            for (int i = 0; i < cols; i++) {
                Long val = 0L;
                for (int j = 0; j < commonCols; j++) {
                    val += Main.getFirstMatrix()[row][j] * Main.getSecondMatrix()[j][i];
                }
                Main.getFirstResultMatrix()[row][i] = val;

                Integer currentColumnCount = Main.getComputedColumns().get(row);
                currentColumnCount++;
                Main.getComputedColumns().set( row, currentColumnCount );
            }
        }

        return currentRow;
    }
}
